package com.tapxsouls.hackingtools.models;

import android.app.Activity;

public class HackingTool {
    private String name;
    private String icon;
    private String description;
    private String category;
    private Class<? extends Activity> activityClass;
    private boolean isInstalled;
    private String version;

    public HackingTool(String name, String icon, String description, String category, 
                      Class<? extends Activity> activityClass) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.category = category;
        this.activityClass = activityClass;
        this.isInstalled = false;
        this.version = "1.0";
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Class<? extends Activity> getActivityClass() { return activityClass; }
    public void setActivityClass(Class<? extends Activity> activityClass) { 
        this.activityClass = activityClass; 
    }
    
    public boolean isInstalled() { return isInstalled; }
    public void setInstalled(boolean installed) { this.isInstalled = installed; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
