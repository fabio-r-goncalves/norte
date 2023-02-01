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
        

        try {
            FileWriter fileWriter = FileWriter.getInstance();
            fileWriter.writeToFileVehicle(getOperatingSystem().getId() + ","
                                                + getOperatingSystem().getNavigationModule().getCurrentRoute().getId() + ","
                                                + getOperatingSystem().getVehicleData().getVehicleConsumptions().getAllConsumptions().getFuel() + "," 
                                                + getOperatingSystem().getVehicleData().getVehicleEmissions().getAllEmissions().getCo() + ","
                                                + getOperatingSystem().getVehicleData().getVehicleEmissions().getAllEmissions().getCo2() + ","
                                                + getOperatingSystem().getVehicleData().getVehicleEmissions().getAllEmissions().getHc() + ","
                                                + getOperatingSystem().getVehicleData().getVehicleEmissions().getAllEmissions().getNox() + ","
                                                + getOperatingSystem().getVehicleData().getVehicleEmissions().getAllEmissions().getPmx() + ","
                                                + getOperatingSystem().getVehicleData().getDistanceDriven() + ","
                                                + stopped_time
                                                );
                                                            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        VehicleSimDataStore dataStore = VehicleSimDataStore.getInstance();
        
        
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
                
                HashMap<String, VehicleSimData> simDataMap = dataStore.getInstance().getVehicleSimData();
                for (Map.Entry<String, VehicleSimData> entry: simDataMap.entrySet()) {
                    fuel = entry.getValue().getFuel() + fuel;
                    co = entry.getValue().getCo() + co;
                    co2 = entry.getValue().getCo2() + co2;
                    hc = entry.getValue().getHc() + hc;
                    nox = entry.getValue().getNox() + nox;
                    pmx = entry.getValue().getPmx() + pmx;
                    stopedTime = entry.getValue().getStopTime() + stopedTime;
                    
                }

                fuel = fuel / simDataMap.size();
                co = co / simDataMap.size();
                co2 = co2 / simDataMap.size();
                hc = hc / simDataMap.size();
                nox = nox /simDataMap.size();
                pmx = pmx / simDataMap.size();
                stopedTime = stopedTime / simDataMap.size();
                
                
                
                try {
                    FileWriter fileWriter = FileWriter.getInstance();
                    fileWriter.writeToFileAverage (
                                        fuel + "," + 
                                        co  + "," +
                                        co2  + "," +
                                        hc  + "," +
                                        nox  + "," +
                                        pmx  + "," +
                                        stopedTime);
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
        
        if(getOperatingSystem().getSimulationTime() / TIME.SECOND > startTime){   
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
                data.vehicleDatatoSimData(getOperatingSystem().getVehicleData().getVehicleEmissions(), stopped_time, getOperatingSystem().getVehicleData().getVehicleConsumptions().getAllConsumptions().getFuel());
                dataStore.addVehicleSimData(data, getOperatingSystem().getId());
            }
        }
        
    }
    
}
