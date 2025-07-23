package com.tapxsouls.hackingtools.utils;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class NetworkScanner {
    
    private static final int[] COMMON_PORTS = {
        21, 22, 23, 25, 53, 80, 110, 111, 135, 139, 143, 443, 993, 995, 1723, 3306, 3389, 5432, 5900, 8080
    };
    
    private static final int TIMEOUT = 1000;
    private ExecutorService executor;

    public NetworkScanner() {
        executor = Executors.newFixedThreadPool(50);
    }

    public List<String> performScan(String target, String scanType, boolean aggressive) {
        List<String> results = new ArrayList<>();
        
        try {
            InetAddress targetAddress = InetAddress.getByName(target);
            
            switch (scanType) {
                case "TCP SYN Scan":
                case "TCP Connect Scan":
                    results = performPortScan(targetAddress, aggressive);
                    break;
                case "Ping Sweep":
                    results = performPingSweep(targetAddress);
                    break;
                case "OS Detection":
                    results = performOSDetection(targetAddress);
                    break;
                default:
                    results = performPortScan(targetAddress, aggressive);
            }
            
        } catch (Exception e) {
            results.add("Error: " + e.getMessage());
        }
        
        return results;
    }

    private List<String> performPortScan(InetAddress target, boolean aggressive) {
        List<String> openPorts = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();
        
        int[] portsToScan = aggressive ? generatePortRange(1, 65535) : COMMON_PORTS;
        
        for (int port : portsToScan) {
            futures.add(executor.submit(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(target, port), TIMEOUT);
                    return port;
                } catch (Exception e) {
                    return -1;
                }
            }));
        }
        
        for (Future<Integer> future : futures) {
            try {
                Integer port = future.get(TIMEOUT + 500, TimeUnit.MILLISECONDS);
                if (port > 0) {
                    String service = getServiceName(port);
                    openPorts.add("Port " + port + "/tcp open " + service);
                }
            } catch (Exception e) {
                // Port closed or timeout
            }
        }
        
        return openPorts;
    }

    private List<String> performPingSweep(InetAddress target) {
        List<String> results = new ArrayList<>();
        String baseNetwork = target.getHostAddress().substring(0, target.getHostAddress().lastIndexOf('.'));
        
        for (int i = 1; i < 255; i++) {
            try {
                InetAddress addr = InetAddress.getByName(baseNetwork + "." + i);
                if (addr.isReachable(TIMEOUT)) {
                    results.add("Host " + addr.getHostAddress() + " is alive");
                }
            } catch (Exception e) {
                // Host unreachable
            }
        }
        
        return results;
    }

    private List<String> performOSDetection(InetAddress target) {
        List<String> results = new ArrayList<>();
        
        try {
            // Simple TTL-based OS fingerprinting
            String osGuess = detectOSByTTL(target);
            results.add("OS Detection: " + osGuess);
            
            // Banner grabbing
            String httpBanner = grabBanner(target, 80);
            if (httpBanner != null) {
                results.add("HTTP Banner: " + httpBanner);
            }
            
            String sshBanner = grabBanner(target, 22);
            if (sshBanner != null) {
                results.add("SSH Banner: " + sshBanner);
            }
            
        } catch (Exception e) {
            results.add("OS Detection failed: " + e.getMessage());
        }
        
        return results;
    }

    private String detectOSByTTL(InetAddress target) {
        // Simplified OS detection based on TTL values
        return "Unable to determine OS (requires raw sockets)";
    }

    private String grabBanner(InetAddress target, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(target, port), TIMEOUT);
            socket.getInputStream().read(new byte[1024]);
            return "Service detected on port " + port;
        } catch (Exception e) {
            return null;
        }
    }

    private String getServiceName(int port) {
        Map<Integer, String> services = new HashMap<>();
        services.put(21, "ftp");
        services.put(22, "ssh");
        services.put(23, "telnet");
        services.put(25, "smtp");
        services.put(53, "dns");
        services.put(80, "http");
        services.put(110, "pop3");
        services.put(143, "imap");
        services.put(443, "https");
        services.put(993, "imaps");
        services.put(995, "pop3s");
        services.put(3306, "mysql");
        services.put(3389, "rdp");
        services.put(5432, "postgresql");
        services.put(8080, "http-proxy");
        
        return services.getOrDefault(port, "unknown");
    }

    private int[] generatePortRange(int start, int end) {
        int[] ports = new int[end - start + 1];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = start + i;
        }
        return ports;
    }
}
