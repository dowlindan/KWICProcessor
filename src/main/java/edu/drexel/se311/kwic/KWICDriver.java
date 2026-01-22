package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.*;
import edu.drexel.se311.kwic.io.*;
import edu.drexel.se311.kwic.sorting.*;
import edu.drexel.se311.kwic.sentenceprocessing.*;

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

    private void displayUsage() {
        outputStrategy.display("Usage: kwic | search <keyword> | index | quit");
    }

    public void run() {
        if (this.sentences == null || this.sentences.isEmpty()) {
            outputStrategy.display("No sentences to process.");
            return;
        }

        inputStrategy.open();
        displayUsage();
        AbstractSentencesProcessor processor;
        while (true) {
            String command = inputStrategy.getCommand().toLowerCase();
            if (Commands.KWIC.equals(command)) {
                processor = new KWICProcessor(this.sentences, new AlphabeticSorter());
            } else if (Commands.KEYWORD_SEARCH.equals(command)) {
                processor = new KeywordSearch(this.sentences, new AlphabeticSorter());
            } else if (Commands.INDEX_GENERATION.equals(command)) {
                processor = new IndexGeneration(this.sentences, new AlphabeticSorter());
            } else if (Commands.QUIT.equals(command)) {
                break;
            } else {
                outputStrategy.display("Invalid command.");
                displayUsage();
                continue;
            }
            List<String> output = processor.getProcessedOutput();
            for (String line : output) {
                outputStrategy.display(line);
            }
        }
        inputStrategy.close();
    }
}