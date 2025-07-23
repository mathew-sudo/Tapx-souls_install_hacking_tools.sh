package com.tapxsouls.hackingtools.activities;

import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.tapxsouls.hackingtools.R;
import com.tapxsouls.hackingtools.utils.NetworkScanner;
import java.util.List;

public class NetworkScannerActivity extends AppCompatActivity {
    
    private EditText targetInput;
    private Button scanButton;
    private TextView resultsText;
    private ProgressBar progressBar;
    private Spinner scanTypeSpinner;
    private CheckBox aggressiveScanBox;
    private NetworkScanner networkScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_scanner);
        
        initializeViews();
        setupSpinner();
        setupClickListeners();
        networkScanner = new NetworkScanner();
    }

    private void initializeViews() {
        targetInput = findViewById(R.id.target_input);
        scanButton = findViewById(R.id.scan_button);
        resultsText = findViewById(R.id.results_text);
        progressBar = findViewById(R.id.progress_bar);
        scanTypeSpinner = findViewById(R.id.scan_type_spinner);
        aggressiveScanBox = findViewById(R.id.aggressive_scan_checkbox);
    }

    private void setupSpinner() {
        String[] scanTypes = {"TCP SYN Scan", "TCP Connect Scan", "UDP Scan", "Ping Sweep", "OS Detection"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, scanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scanTypeSpinner.setAdapter(adapter);
    }

    private void setupClickListeners() {
        scanButton.setOnClickListener(v -> {
            String target = targetInput.getText().toString().trim();
            if (!target.isEmpty()) {
                new ScanTask().execute(target);
            } else {
                Toast.makeText(this, "Please enter a target IP or hostname", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ScanTask extends AsyncTask<String, String, List<String>> {
        @Override
        protected void onPreExecute() {
            scanButton.setEnabled(false);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            resultsText.setText("🔍 Scanning network...\n");
        }

        @Override
        protected List<String> doInBackground(String... targets) {
            String target = targets[0];
            String scanType = scanTypeSpinner.getSelectedItem().toString();
            boolean aggressive = aggressiveScanBox.isChecked();
            
            publishProgress("🎯 Target: " + target);
            publishProgress("📡 Scan Type: " + scanType);
            publishProgress("⚡ Aggressive: " + (aggressive ? "Enabled" : "Disabled"));
            publishProgress("═══════════════════════════════");
            
            return networkScanner.performScan(target, scanType, aggressive);
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            resultsText.append(progress[0] + "\n");
        }

        @Override
        protected void onPostExecute(List<String> results) {
            scanButton.setEnabled(true);
            progressBar.setVisibility(ProgressBar.GONE);
            
            resultsText.append("\n🔥 SCAN RESULTS 🔥\n");
            resultsText.append("═══════════════════════════════\n");
            
            if (results != null && !results.isEmpty()) {
                for (String result : results) {
                    resultsText.append("✅ " + result + "\n");
                }
            } else {
                resultsText.append("❌ No open ports found\n");
            }
            
            resultsText.append("\n📊 Scan completed successfully!");
        }
    }
}
