package edu.drexel.se311.kwic.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TxtOutput extends OutputStrategy {
    private String outputFilename;

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }
    
    @Override
    public void display(String outputString) {
        try {
            Files.writeString(Path.of(this.outputFilename), outputString + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);   
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
