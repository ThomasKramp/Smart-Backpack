package com.example.smartbackpack.Bluetooth;

import java.util.List;

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

    public boolean gotDevice(List<DeviceItem> devices){
        for (DeviceItem device: devices) {
            if (name.equals(device.getName()) && macAddress.equals(device.getMacAddress())) return true;
        }
        return false;
    }
}
