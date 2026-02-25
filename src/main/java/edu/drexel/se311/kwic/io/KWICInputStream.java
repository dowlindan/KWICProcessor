package edu.drexel.se311.kwic.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KWICInputStream extends InputStrategy {
    private BufferedReader reader;
    private InputStream inputStream;

    public KWICInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public void open() {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    public void close() {
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getCommand() {
        String userInput = null;
        try {
            userInput = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInput;
    }
}