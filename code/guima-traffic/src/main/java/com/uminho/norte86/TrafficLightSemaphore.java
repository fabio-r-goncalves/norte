package com.uminho.norte86;

public class TrafficLightSemaphore {
    private static TrafficLightSemaphore instance = null;
    private boolean changeToGreen;
    private boolean changing;

    private TrafficLightSemaphore(){
        changeToGreen = false;
    }

    public static TrafficLightSemaphore getSemaphore(){
        if(instance == null){
            instance = new TrafficLightSemaphore();
        }

        return instance;
    }

    public void setChangeToGreen(boolean changeToGreen){
        this.changeToGreen = changeToGreen;
    }

    public boolean changeToGreen(){
        return this.changeToGreen;
    }

    public void setChanging (boolean changing){
        this.changing = changing;
    }

    public boolean isChanging (){
        return changing;
    }
}
