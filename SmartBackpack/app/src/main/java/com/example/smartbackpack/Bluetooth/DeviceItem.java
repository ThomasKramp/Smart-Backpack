package com.example.smartbackpack.Bluetooth;

public class DeviceItem {
    private String name;
    private String macAddress;

    public DeviceItem(String name, String macAddress){
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getName() { return name; }
    public String getMacAddress() { return macAddress; }

    public void setName(String name) {
        this.name = name;
    }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
}
