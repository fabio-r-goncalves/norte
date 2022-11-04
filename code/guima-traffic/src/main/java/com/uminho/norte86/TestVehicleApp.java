package com.uminho.norte86;

import org.eclipse.mosaic.fed.application.ambassador.simulation.VehicleParameters.VehicleParametersChangeRequest;
import org.eclipse.mosaic.fed.application.ambassador.simulation.perception.SimplePerceptionConfiguration;
import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.interactions.vehicle.VehicleParametersChange;
import org.eclipse.mosaic.interactions.vehicle.VehicleSensorActivation;
import org.eclipse.mosaic.interactions.vehicle.VehicleSensorActivation.SensorType;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

public class TestVehicleApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {

    @Override
    public void onShutdown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStartup() {
        // TODO Auto-generated method stub
        if(!getOperatingSystem().getId().equals("veh_1")){
            return;
        }
        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 1 * TIME.SECOND;
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime + " phase: " + phaseDuration + " next: " + nextEventTime);
        System.out.println("activating");
        
        //getOperatingSystem().activateVehicleSensors(2, SensorType.RADAR_FRONT);

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
    }

    @Override
    public void processEvent(Event arg0) throws Exception {
        // TODO Auto-generated method stub
        if(!getOperatingSystem().getId().equals("veh_1")){
            return;
        }
        System.out.println("veh type: " + getOperatingSystem().getInitialVehicleType());
        //
        

       
        System.out.println("veh data: " + getOperatingSystem().getNavigationModule().getVehicleData());
        
        
        System.out.println("veh perc: " + getOperatingSystem().getPerceptionModule().getPerceivedVehicles());

        //getOperatingSystem().applyVehicleParametersChange(VehicleParametersChangeRequest-);


        long currentTime = getOs().getSimulationTime();
        long nextEventTime = currentTime + 2 * TIME.SECOND;
        //System.out.println(getOperatingSystem().getTrafficLightGroup().getGroupId() + " now: " + currentTime + " phase: " + phaseDuration + " next: " + nextEventTime);
        final Event nextEvent = new Event(nextEventTime, this);
        getOs().getEventManager().addEvent(nextEvent);
        
    }

    @Override
    public void onVehicleUpdated(VehicleData arg0, VehicleData arg1) {
        // TODO Auto-generated method stub
        
    }
    
}
