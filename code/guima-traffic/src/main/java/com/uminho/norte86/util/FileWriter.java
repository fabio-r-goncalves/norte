package com.uminho.norte86.util;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class FileWriter {
    private static FileWriter instance;
    private final String FILE_NAME = "output-lane.csv";
    private final String FILE_NAME_VEHICLE = "output-vehicle.csv";
    private final String FILE_NAME_AVERAGE = "output-average.csv";
    PrintWriter printWriterDetector;
    PrintWriter printWriterVehicle;
    PrintWriter printWriterAverage;

    private FileWriter() throws IOException{
        File file = new File(FILE_NAME);
        File fileVehicle = new File(FILE_NAME_VEHICLE);
        File fileAverage = new File(FILE_NAME_AVERAGE);
        printWriterDetector = new PrintWriter(new java.io.FileWriter(file), true);
        printWriterVehicle = new PrintWriter(new java.io.FileWriter(fileVehicle), true);
        printWriterAverage = new PrintWriter(new java.io.FileWriter(fileAverage),true);
        
    }
    public static FileWriter getInstance() throws IOException{
        if(instance == null){
            instance = new FileWriter();
        }

        return instance;
    }

    public void writeToFile(String csvLine) throws IOException{
        printWriterDetector.write(csvLine+"\n");
    }
    

    public void writeToFileVehicle(String csvLine) throws IOException{
        printWriterVehicle.write(csvLine+"\n");
        printWriterAverage.flush();
    }

    public void closeVehicleFile(){
        printWriterVehicle.close();
    }

    public void writeToFileAverage(String csvLine) throws IOException{
        printWriterAverage.write(csvLine + "\n");
        printWriterAverage.flush();
    }
}
