package com.tapxsouls.hackingtools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.tapxsouls.hackingtools.adapters.ToolsAdapter;
import com.tapxsouls.hackingtools.models.HackingTool;
import com.tapxsouls.hackingtools.activities.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private GridView toolsGrid;
    private TextView statusText;
    private List<HackingTool> hackingTools;
    private ToolsAdapter toolsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupToolsList();
        setupClickListeners();
        displayBanner();
    }

    private void initializeViews() {
        toolsGrid = findViewById(R.id.tools_grid);
        statusText = findViewById(R.id.status_text);
    }

    private void setupToolsList() {
        hackingTools = new ArrayList<>();
        
        // Network Tools
        hackingTools.add(new HackingTool("Network Scanner", "🔍", 
            "Advanced network discovery and port scanning", "network", NetworkScannerActivity.class));
        hackingTools.add(new HackingTool("WiFi Cracker", "📶", 
            "WPA/WEP WiFi password cracking suite", "wifi", WifiCrackerActivity.class));
        hackingTools.add(new HackingTool("Packet Sniffer", "📡", 
            "Network traffic analysis and monitoring", "network", PacketSnifferActivity.class));
        
        // Exploitation Tools
        hackingTools.add(new HackingTool("Payload Generator", "💣", 
            "Generate custom payloads and shells", "exploit", PayloadGeneratorActivity.class));
        hackingTools.add(new HackingTool("Exploit Framework", "⚔️", 
            "Advanced exploitation and post-exploitation", "exploit", ExploitFrameworkActivity.class));
        hackingTools.add(new HackingTool("SQL Injector", "💉", 
            "Automated SQL injection testing", "web", SqlInjectorActivity.class));
        
        // Web Security Tools
        hackingTools.add(new HackingTool("Web Crawler", "🕷️", 
            "Website vulnerability scanner", "web", WebCrawlerActivity.class));
        hackingTools.add(new HackingTool("XSS Tester", "🔓", 
            "Cross-site scripting vulnerability finder", "web", XssTesterActivity.class));
        hackingTools.add(new HackingTool("Directory Buster", "📁", 
            "Hidden directory and file discovery", "web", DirectoryBusterActivity.class));
        
        // Custom Tools
        hackingTools.add(new HackingTool("Phishing Kit", "🎣", 
            "Social engineering toolkit", "social", PhishingKitActivity.class));
        hackingTools.add(new HackingTool("OSINT Gatherer", "🔎", 
            "Open source intelligence collection", "recon", OsintGathererActivity.class));
        hackingTools.add(new HackingTool("Custom Scripts", "⚙️", 
            "User-defined automation scripts", "custom", CustomToolsActivity.class));
        
        // Advanced Tools
        hackingTools.add(new HackingTool("Backdoor Generator", "🚪", 
            "Persistent access tool generator", "backdoor", BackdoorGeneratorActivity.class));
        hackingTools.add(new HackingTool("Crypto Cracker", "🔐", 
            "Hash and encryption breaking tools", "crypto", CryptoCrackerActivity.class));
        hackingTools.add(new HackingTool("Mobile Forensics", "📱", 
            "Android/iOS security analysis", "forensics", MobileForensicsActivity.class));

        toolsAdapter = new ToolsAdapter(this, hackingTools);
        toolsGrid.setAdapter(toolsAdapter);
    }

    private void setupClickListeners() {
        toolsGrid.setOnItemClickListener((parent, view, position, id) -> {
            HackingTool tool = hackingTools.get(position);
            Intent intent = new Intent(MainActivity.this, tool.getActivityClass());
            intent.putExtra("tool_name", tool.getName());
            startActivity(intent);
        });
    }

    private void displayBanner() {
        String banner = "💀 TAPX-SOULS v2.0 💀\n" +
                       "Advanced Mobile Hacking Suite\n" +
                       "═══════════════════════════════\n" +
                       "🔥 " + hackingTools.size() + " Tools Available 🔥";
        statusText.setText(banner);
    }
}
