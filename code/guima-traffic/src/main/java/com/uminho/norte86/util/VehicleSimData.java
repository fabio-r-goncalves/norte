package com.uminho.norte86.util;

import org.eclipse.mosaic.lib.objects.vehicle.VehicleEmissions;

public class VehicleSimData {
    private double fuel;
    private double co2;
    private double co;
    private double hc;
    private double pmx;
    private double nox;
    private double stopTime;
    


    public VehicleSimData(double fuel, double co2, double co, double hc, double pmx, double nox, double stopTime) {
        this.fuel = fuel;
        this.co2 = co2;
        this.co = co;
        this.hc = hc;
        this.pmx = pmx;
        this.nox = nox;
        this.stopTime = stopTime;
        
    }

    


    public VehicleSimData() {
    }




    public double getFuel() {
        return fuel;
    }


    public void setFuel(double fuel) {
        this.fuel = fuel;
    }


    public double getCo2() {
        return co2;
    }


    public void setCo2(double co2) {
        this.co2 = co2;
    }


    public double getCo() {
        return co;
    }


    public void setCo(double co) {
        this.co = co;
    }


    public double getHc() {
        return hc;
    }


    public void setHc(double hc) {
        this.hc = hc;
    }


    public double getPmx() {
        return pmx;
    }


    public void setPmx(double pmx) {
        this.pmx = pmx;
    }


    public double getNox() {
        return nox;
    }


    public void setNox(double nox) {

        this.nox = nox;
    }


    public double getStopTime() {
        return stopTime;
    }


    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
    }


  

    public void vehicleDatatoSimData(VehicleEmissions emissions, double stopTime, double fuel){
        this.setCo(emissions.getAllEmissions().getCo());
        this.setCo2(emissions.getAllEmissions().getCo2());
        this.setHc(emissions.getAllEmissions().getHc());
        this.setNox(emissions.getAllEmissions().getNox());
        this.setPmx(emissions.getAllEmissions().getPmx());
        this.setFuel(fuel);
        this.setStopTime(stopTime);
        
    }

    
}
