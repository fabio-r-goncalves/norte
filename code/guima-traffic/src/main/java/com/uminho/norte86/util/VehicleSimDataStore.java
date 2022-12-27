package com.uminho.norte86.util;


import java.util.HashMap;


public class VehicleSimDataStore {
    private static VehicleSimDataStore instance = null;
    private HashMap<String, VehicleSimData> vehicleSimDataMap;
    private boolean stored;

    private VehicleSimDataStore(){
        vehicleSimDataMap = new HashMap<>();
        stored = false;
    }

    public static VehicleSimDataStore getInstance(){
        if(instance == null){
            instance = new VehicleSimDataStore();
        }

        return instance;
    }

    public void addVehicleSimData(VehicleSimData vehicleData, String vehicleID){
        vehicleSimDataMap.put(vehicleID, vehicleData);
    }

    public HashMap<String, VehicleSimData> getVehicleSimData(){
        return vehicleSimDataMap;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    
}
