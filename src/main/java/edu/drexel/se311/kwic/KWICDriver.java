package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.*;
import edu.drexel.se311.kwic.io.*;
import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sentenceprocessing.*;
import edu.drexel.se311.kwic.sorting.*;
import edu.drexel.se311.kwic.textparsing.NewlineTextParser;
import java.util.ArrayList;
import java.util.List;

public class KWICDriver {
    private InputStrategy inputStrategy;
    private OutputStrategy outputStrategy; 
    private List<Line> lines;

    public KWICDriver(InputStrategy inputStrategy, OutputStrategy outputStrategy) {
        this.inputStrategy = inputStrategy;
        this.outputStrategy = outputStrategy;
    }

    public int loadFile(String filename) {
        AbstractFileParser parser;
        if (filename.endsWith(".txt")) {
            parser = new PlaintextFileParser(new NewlineTextParser());
        } else {
            outputStrategy.display("Unsupported file format: " + filename);
            return 1;
        }

        parser.setFilePath(filename);
        
        try {
            this.lines = parser.getSentencesAsLines();
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
        if (this.lines == null || this.lines.isEmpty()) {
            outputStrategy.display("No sentences to process.");
            return;
        }

        inputStrategy.open();
        displayUsage();
        AbstractSentencesProcessor processor;
        while (true) {
            String command = inputStrategy.getCommand();
            if (Commands.KWIC.equals(command)) {
                processor = new KWICProcessor(this.lines, new AlphabeticSorter());
            } else if (command.startsWith(Commands.KEYWORD_SEARCH)) {
                String keyword = command.substring(Commands.KEYWORD_SEARCH.length()).trim();
                if (keyword.isEmpty()) {
                    outputStrategy.display("Keyword search requires a keyword.");
                    displayUsage();
                    continue;
                }
                List<Line> linesWithKeyword = new ArrayList<>(this.lines);
                linesWithKeyword.add(0, new Line(keyword, -1)); // Add keyword as first line
                //  This is horrible design but I am out of time
                processor = new KeywordSearch(linesWithKeyword, new AlphabeticSorter());
            } else if (Commands.INDEX_GENERATION.equals(command)) {
                processor = new IndexGeneration(this.lines, new AlphabeticSorter());
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