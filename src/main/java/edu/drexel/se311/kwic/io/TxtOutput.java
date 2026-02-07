package edu.drexel.se311.kwic.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TxtOutput extends OutputStrategy {
    private boolean firstLineWritten = false;
    private String outputFilename;

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }
    
    @Override
    public void display(String outputString) {
        try {
            if (!firstLineWritten) {
                Files.writeString(Path.of(this.outputFilename), outputString + "\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                firstLineWritten = true;
            } else {
                Files.writeString(Path.of(this.outputFilename), outputString + "\n", StandardOpenOption.APPEND, StandardOpenOption.CREATE);   
            }
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
