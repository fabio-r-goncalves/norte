package com.uminho.norte86.apps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

import com.uminho.norte86.util.FileWriter;
import com.uminho.norte86.util.VehicleSimData;
import com.uminho.norte86.util.VehicleSimDataStore;

public class TestVehicleApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {
    private long stopped_time;
    private long stop_time;
    private final int startTime = 500;
    

    @Override
    public void onShutdown() {
        
       

        VehicleSimDataStore dataStore = VehicleSimDataStore.getInstance();
        VehicleSimData data;
        if((data = dataStore.getVehicleSimData().get(getOperatingSystem().getId())) != null ){
            if(getOperatingSystem().getNavigationModule().getCurrentRoute().getLength() >= data.getDistanceDriven())
            data.setEndRoute(true);
        }
         
        
        if(getOperatingSystem().getSimulationTime() >= Long.parseLong("4100000000000")){
            
        //if(getOperatingSystem().getSimulationTime() >= Long.parseLong("500000000000")){    
            if(!dataStore.isStored()){
                dataStore.setStored(true);
                double fuel = 0;
                double co = 0;
                double co2 = 0;
                double hc = 0;
                double nox = 0;
                double pmx = 0;
                double stopedTime = 0;
                double stopedTimeMax = 0;
                double distanceDrien = 0;
                int endTrips = 0;
                int notEnded = 0;
                
                HashMap<String, VehicleSimData> simDataMap = dataStore.getInstance().getVehicleSimData();
                
                for (Map.Entry<String, VehicleSimData> entry: simDataMap.entrySet()) {
                    fuel = entry.getValue().getFuel() + fuel;
                    co = entry.getValue().getCo() + co;
                    co2 = entry.getValue().getCo2() + co2;
                    hc = entry.getValue().getHc() + hc;
                    nox = entry.getValue().getNox() + nox;
                    pmx = entry.getValue().getPmx() + pmx;
                    stopedTime = entry.getValue().getStopTime() + stopedTime;
                    distanceDrien = entry.getValue().getDistanceDriven() + distanceDrien;
                    if(entry.getValue().isEndRoute()){
                        endTrips = endTrips + 1;
                    }else{
                        notEnded = notEnded + 1;
                    }
                    if(entry.getValue().getStopTime() > stopedTimeMax){
                        stopedTimeMax = entry.getValue().getStopTime();
                    }

                    try {
                        FileWriter.getInstance().writeToFileVehicle(entry.getKey() + ","
                                                    + entry.getValue().getRoute()+ ","
                                                    + entry.getValue().getFuel() + "," 
                                                    + entry.getValue().getCo() + ","
                                                    + entry.getValue().getCo2() + ","
                                                    + entry.getValue().getHc() + ","
                                                    + entry.getValue().getNox() + ","
                                                    + entry.getValue().getPmx() + ","
                                                    + entry.getValue().getDistanceDriven() + ","
                                                    + entry.getValue().getStopTime() + ","
                                                    + entry.getValue().isEndRoute()
                                                    );
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                

                fuel = fuel / simDataMap.size();
                co = co / simDataMap.size();
                co2 = co2 / simDataMap.size();
                hc = hc / simDataMap.size();
                nox = nox /simDataMap.size();
                pmx = pmx / simDataMap.size();
                stopedTime = stopedTime / simDataMap.size();
                distanceDrien = distanceDrien / simDataMap.size();
                
                
                
                try {
                    FileWriter fileWriter = FileWriter.getInstance();
                    fileWriter.writeToFileAverage (
                                        fuel + "," + 
                                        co  + "," +
                                        co2  + "," +
                                        hc  + "," +
                                        nox  + "," +
                                        pmx  + "," +
                                        stopedTime + "," +
                                        stopedTimeMax + "," + 
                                        distanceDrien + "," + 
                                        endTrips + "," +
                                        notEnded
                                        );
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    FileWriter.getInstance().closeVehicleFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
    }

    @Override
    public void onStartup() {
        stop_time = -1;
        stopped_time = 0;
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onVehicleUpdated(VehicleData arg0, VehicleData arg1) {

        
        
        if(getOperatingSystem().getSimulationTime() >= Long.parseLong("500000000000") && getOperatingSystem().getSimulationTime() < Long.parseLong("4100000000000")){
            if(getOperatingSystem().getVehicleData().getSpeed() == (double)0.0){
                if(stop_time == -1){
                    stop_time = getOperatingSystem().getSimulationTime();
                }
                
            }else {
                stop_time = -1;
            }

            if(stop_time != -1){
                stopped_time = stopped_time + getOperatingSystem().getSimulationTime() - stop_time;
                stop_time = getOperatingSystem().getSimulationTime();
                
            }
            VehicleSimDataStore dataStore = VehicleSimDataStore.getInstance();
            VehicleSimData data = new VehicleSimData();
            if((getOperatingSystem().getNavigationModule().getCurrentRoute().getLength() - (getOperatingSystem().getNavigationModule().getCurrentRoute().getLength() / 10)) <= getOperatingSystem().getVehicleData().getDistanceDriven()){
                data.vehicleDatatoSimData(
                    getOperatingSystem().getVehicleData().getVehicleEmissions(), 
                    stopped_time, 
                    getOperatingSystem().getVehicleData().getVehicleConsumptions().getAllConsumptions().getFuel(),
                    getOperatingSystem().getNavigationModule().getVehicleData().getDistanceDriven(),
                    getOperatingSystem().getNavigationModule().getCurrentRoute().getId()
                    );
                dataStore.addVehicleSimData(data, getOperatingSystem().getId());
            }
        }
        
    }
    
}
