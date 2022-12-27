package com.uminho.norte86.apps;

import java.util.Collection;

import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.InductionLoop;
import org.eclipse.mosaic.fed.application.ambassador.simulation.tmc.LaneAreaDetector;
import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.TrafficManagementCenterApplication;
import org.eclipse.mosaic.fed.application.app.api.os.TrafficManagementCenterOperatingSystem;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

public class TestTrafficManagementApp extends AbstractApplication<TrafficManagementCenterOperatingSystem> implements TrafficManagementCenterApplication {
    private final int startTime = 500;

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

       
        
        
    }

    @Override
    public void onInductionLoopUpdated(Collection<InductionLoop> arg0) {
        if(getOperatingSystem().getSimulationTime() / TIME.SECOND > startTime){   
            StringBuffer sb = new StringBuffer();
            for (InductionLoop inductionLoop : arg0) {
                sb.append(inductionLoop.getId() + ":\t" + inductionLoop.getTrafficFlowVehPerHour()+"\t-\t");
            }
            System.out.println(sb.toString());
        }
    }

    @Override
    public void onLaneAreaDetectorUpdated(Collection<LaneAreaDetector> arg0) {
        for (LaneAreaDetector laneAreaDetector : arg0) {
            System.out.println("I am a detector: " + laneAreaDetector.getId() + " " + laneAreaDetector.getMeanSpeed() + " " + laneAreaDetector.getTrafficDensity() + " " + laneAreaDetector.getAmountOfVehiclesOnSegment() + " " + getOperatingSystem().getSimulationTime() / TIME.SECOND);
        }
    }
    
}
