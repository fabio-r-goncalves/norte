package com.uminho.norte86;

import java.util.Collection;

import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.InductionLoop;
import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.LaneAreaDetector;
import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.TrafficManagementCenterApplication;
import org.eclipse.mosaic.fed.application.app.api.os.TrafficManagementCenterOperatingSystem;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

public class TestTrafficManagementApp extends AbstractApplication<TrafficManagementCenterOperatingSystem> implements TrafficManagementCenterApplication {

    @Override
    public void onShutdown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStartup() {
        // TODO Auto-generated method stub  
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 1 * TIME.SECOND;
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime + " phase: " + phaseDuration + " next: " + nextEventTime);
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        // TODO Auto-generated method stub

        LaneAreaDetector areaDetector = getOperatingSystem().getLaneAreaDetector("detector_0");
        System.out.println(areaDetector.getAmountOfVehiclesOnSegment());
        
        
    }

    @Override
    public void onInductionLoopUpdated(Collection<InductionLoop> arg0) {
        // TODO Auto-generated method stub
        for (InductionLoop inductionLoop : arg0) {
           // System.out.println("I am loop: " + inductionLoop.getId() + " passed vehicles " + inductionLoop.getAmountOfPassedVehicles() + " avg speed " + inductionLoop.getAverageSpeedMs() + " flow " + inductionLoop.getTrafficFlowVehPerHour());
        }
    }

    @Override
    public void onLaneAreaDetectorUpdated(Collection<LaneAreaDetector> arg0) {
        // TODO Auto-generated method stub
        
       
        
    }
    
}
