package com.uminho.norte86.apps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.InductionLoop;
import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.LaneAreaDetector;
import org.eclipse.mosaic.fed.application.app.ConfigurableApplication;
import org.eclipse.mosaic.fed.application.app.api.TrafficManagementCenterApplication;
import org.eclipse.mosaic.fed.application.app.api.os.TrafficManagementCenterOperatingSystem;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

import com.uminho.norte86.conf_reader.TrafficManagementConfigReader;

public class TestTrafficManagementApp extends ConfigurableApplication<TrafficManagementConfigReader,TrafficManagementCenterOperatingSystem> implements TrafficManagementCenterApplication {
    
    private TrafficManagementConfigReader trafficManReader;
    private HashMap<String, Integer> inductionLoopVehicles;


    public TestTrafficManagementApp(){
        super(TrafficManagementConfigReader.class, "traffic_management_config");
    }

    @Override
    public void onShutdown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStartup() {
        // TODO Auto-generated method stub  
        this.trafficManReader = this.getConfiguration();
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 10 * TIME.SECOND;
        final Event nextEvent = new Event(nextEventTime, this);
        this.inductionLoopVehicles = new HashMap<>();
        getOs().getEventManager().addEvent(nextEvent);
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 1 * TIME.SECOND;
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
        /*

        System.out.println("time: "+getOperatingSystem().getSimulationTime());
        for (Map.Entry<String,Integer> set: inductionLoopVehicles.entrySet()) {
            System.out.println(set.getKey() + ": " + set.getValue());    
        }
        System.out.println("\n\n");
        */
    }

    @Override
    public void onInductionLoopUpdated(Collection<InductionLoop> arg0) {
        long time = getOperatingSystem().getSimulationTime() / TIME.SECOND;
        if(time > trafficManReader.startTime && time < trafficManReader.stopTime){   
            for (InductionLoop inductionLoop : arg0) {
                if(!inductionLoopVehicles.containsKey(inductionLoop.getId())){
                    inductionLoopVehicles.put(inductionLoop.getId(), inductionLoop.getAmountOfPassedVehicles());
                }else{
                    inductionLoopVehicles.put(inductionLoop.getId(), inductionLoopVehicles.get(inductionLoop.getId()) + inductionLoop.getAmountOfPassedVehicles());
                }
            }
        }
    }

    @Override
    public void onLaneAreaDetectorUpdated(Collection<LaneAreaDetector> arg0) {
        /* 
        for (LaneAreaDetector laneAreaDetector : arg0) {
            System.out.println("I am a detector: " + laneAreaDetector.getId() + " " + laneAreaDetector.getMeanSpeed() + " " + laneAreaDetector.getTrafficDensity() + " " + laneAreaDetector.getAmountOfVehiclesOnSegment() + " " + getOperatingSystem().getSimulationTime() / TIME.SECOND);
        }

        */
    }

}
