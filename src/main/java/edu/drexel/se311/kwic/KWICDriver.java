package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.*;
import edu.drexel.se311.kwic.io.*;
import java.util.List;

public class KWICDriver {
    private InputStrategy inputStrategy;
    private OutputStrategy outputStrategy; 
    private List<String> sentences;

    public KWICDriver(InputStrategy inputStrategy, OutputStrategy outputStrategy) {
        this.inputStrategy = inputStrategy;
        this.outputStrategy = outputStrategy;
    }

    public int loadFile(String filename) {
        AbstractFileParser parser;
        if (filename.endsWith(".txt")) {
            parser = new PlaintextFileParser();
        } else {
            outputStrategy.display("Unsupported file format: " + filename);
            return 1;
        }

        parser.setFilePath(filename);
        
        try {
            this.sentences = parser.getSentencesAsList();
            return 0;
        } catch (Exception e) {
            outputStrategy.display("Error reading file: " + e.getMessage());
            return 1;
        }
    
    }

    public void run() {
        System.out.println(this.sentences);
    }
}