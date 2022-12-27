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
        VehicleSimData data = new VehicleSimData();
        if((getOperatingSystem().getNavigationModule().getCurrentRoute().getLength() - (getOperatingSystem().getNavigationModule().getCurrentRoute().getLength() / 10)) <= getOperatingSystem().getVehicleData().getDistanceDriven()){
            data.vehicleDatatoSimData(getOperatingSystem().getVehicleData().getVehicleEmissions(), stop_time, getOperatingSystem().getVehicleData().getVehicleConsumptions().getAllConsumptions().getFuel());
            dataStore.addVehicleSimData(data, getOperatingSystem().getId());
        }

        if(getOperatingSystem().getSimulationTime() >= Long.parseLong("1999000000000")){
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
        /* 
        
        if(!getOperatingSystem().getId().equals("veh_1")){
            return;
        }
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 1 * TIME.SECOND;
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime + " phase: " + phaseDuration + " next: " + nextEventTime);
        System.out.println("activating");
        

        SimplePerceptionConfiguration configuration = new SimplePerceptionConfiguration(100, 500);
        
        getOperatingSystem().getPerceptionModule().enable(configuration);

        System.out.println("enable sensors " + getOperatingSystem().getVehicleParameters().getInitialVehicleType());

        SensorType sensorType = VehicleSensorActivation.SensorType.LIDAR;
        SensorType sensorTypeFront = VehicleSensorActivation.SensorType.RADAR_REAR;
        VehicleSensorActivation activation = new VehicleSensorActivation(10, "veh_1", 10, new SensorType[]{SensorType.LIDAR});
                
        //getOperatingSystem().activateVehicleSensors(2, sensorType);
        System.out.println("done");
        getOperatingSystem().activateVehicleSensors(2, activation.getSensorTypes());
        System.out.println("done2");
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
        
        
        System.out.println(getOperatingSystem().getId());
        */
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onVehicleUpdated(VehicleData arg0, VehicleData arg1) {
        if(getOperatingSystem().getSimulationTime() / TIME.SECOND > startTime){   
            if(getOperatingSystem().getVehicleData().getSpeed() == (double)0){
                if(stop_time == -1){
                    stop_time = getOperatingSystem().getSimulationTime();
                }else{
                    stopped_time = stopped_time + getOperatingSystem().getSimulationTime() - stop_time;
                    stop_time = -1;
                }
            }
        }
        
    }
    
}
