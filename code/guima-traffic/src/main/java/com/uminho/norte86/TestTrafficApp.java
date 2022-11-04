package com.uminho.norte86;


import java.util.ArrayList;

import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.TrafficLightApplication;
import org.eclipse.mosaic.fed.application.app.api.os.TrafficLightOperatingSystem;
import org.eclipse.mosaic.lib.objects.trafficlight.TrafficLightGroupInfo;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;


public class TestTrafficApp extends AbstractApplication<TrafficLightOperatingSystem> implements TrafficLightApplication {
    boolean greenLightOn = true;
    @Override
    public void onShutdown() {
        
    }

    @Override
    public void onStartup() {

        if(!getOperatingSystem().getTrafficLightGroup().getGroupId().equals("262730111")){
            getOperatingSystem().switchToProgram("1");
            System.out.println("changed traffic ligtht to " + getOperatingSystem().getId());
        }

        System.out.println("Hello " + getOperatingSystem().getTrafficLightGroup().getGroupId());
        if(!getOperatingSystem().getTrafficLightGroup().getGroupId().equals("cluster_262730102_485963521_5060533481")){
            return;
        }
        long currentTime = getOs().getSimulationTime();
        long phaseDuration = getOperatingSystem().getCurrentPhase().getRemainingDuration();
        long nextEventTime = currentTime + phaseDuration;
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime + " phase: " + phaseDuration + " next: " + nextEventTime);
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
        
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        //System.out.println("My ID " + getOperatingSystem().getTrafficLightGroup().getGroupId());
        if(!getOperatingSystem().getTrafficLightGroup().getGroupId().equals("cluster_262730102_485963521_5060533481")){
            return;
        }

        System.out.println("controlled lanes: " + getOperatingSystem().getControlledLanes());
        System.out.println("tlg: " + getOperatingSystem().getTrafficLightGroup());
        
        System.out.println("phase" + getOperatingSystem().getCurrentPhase());
        System.out.println("program: " + getOperatingSystem().getCurrentProgram());
        
        /* 
        if(!greenLightOn){
            setGreenLight();
        }else{
            setRedLight();
        } 
        */
        
    }

    public void setGreenLight(){
        //System.out.println("program: "+ getOperatingSystem().getCurrentProgram().getProgramId() + " phase: "+getOperatingSystem().getCurrentPhase().getIndex() + " dur: " + getOperatingSystem().getCurrentPhase().getRemainingDuration());
        System.out.println("Before Green");
       
        

        //getOperatingSystem().switchToProgram("0");
        getOperatingSystem().switchToPhaseIndex(0);
               
        //getOperatingSystem().setRemainingDurationOfCurrentPhase(10 * TIME.SECOND);
        
    
        greenLightOn = true;
       
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime  + " next: " + nextEventTime + " remaining phase: "+ getOperatingSystem().getCurrentPhase().getRemainingDuration());
      

        nextEvent(10000, "green");
        
    }
    public void setRedLight(){
        System.out.println("Before Red");
        //System.out.println("program: "+ getOperatingSystem().getCurrentProgram().getProgramId() + " phase: "+getOperatingSystem().getCurrentPhase().getIndex() + " dur: " + getOperatingSystem().getCurrentPhase().getRemainingDuration());
        
        //System.out.println("changed Program1");
        
        getOperatingSystem().switchToProgram("1");
        getOperatingSystem().switchToPhaseIndex(0);
        
        //getOperatingSystem().setRemainingDurationOfCurrentPhase(2 * TIME.SECOND);

        greenLightOn = false;
        nextEvent(3000, "red");
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime  + " next: " + nextEventTime + " remaining phase: "+ getOperatingSystem().getCurrentPhase().getRemainingDuration());
        
    }

    private void nextEvent(long time, String color){
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + time * TIME.MILLI_SECOND ;
        System.out.println("current: "+currentTime+" next: "+nextEventTime+" color: "+color);
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
        
    }

    @Override
    public void onTrafficLightGroupUpdated(TrafficLightGroupInfo arg0, TrafficLightGroupInfo arg1) {
        /* 
        if(!getOperatingSystem().getTrafficLightGroup().getGroupId().equals("262730111")){
            System.out.println("changing light: "+getOperatingSystem().getSimulationTimeMs() + " " + getOperatingSystem().getCurrentProgram() + " " +getOperatingSystem().getCurrentPhase());
            return;
        }
        if(TrafficLightSemaphore.getSemaphore().changeToGreen() && getOperatingSystem().getCurrentProgram().getProgramId().equals("0")){
            getOperatingSystem().switchToProgram("1");
            if(getOperatingSystem().getCurrentPhase().getIndex() == 1){
                getOperatingSystem().setRemainingDurationOfCurrentPhase(0);
            }else{
                getOperatingSystem().setRemainingDurationOfCurrentPhase(3);
            }

            TrafficLightSemaphore.getSemaphore().setChangeToGreen(false);
        }else  if ( !TrafficLightSemaphore.getSemaphore().changeToGreen() && TrafficLightSemaphore.getSemaphore().isChanging() && getOperatingSystem().getCurrentProgram().getProgramId().equals("1")){
            TrafficLightSemaphore.getSemaphore().setChanging(false);
        }
        */
    }
    
}
