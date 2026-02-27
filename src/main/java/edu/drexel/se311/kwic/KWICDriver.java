package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.*;
import edu.drexel.se311.kwic.io.*;
import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sentenceprocessing.*;
import edu.drexel.se311.kwic.sorting.*;
import java.util.List;
import java.util.Set;

public class KWICDriver {
    private AbstractFileParser fileParser;
    private InputStrategy inputStrategy;
    private OutputStrategy outputStrategy; 
    private SortingStrategy sortingStrategy;
    private boolean filterWords;
    private Set<String> trivialWords;
    private String keyword;
    
    private List<Line> lines;

    public KWICDriver(String filename, AbstractFileParser fileParser, InputStrategy inputStrategy, OutputStrategy outputStrategy, SortingStrategy sortingStrategy, 
        boolean filterWords, Set<String> trivialWords, String keyword) {
        this.fileParser = fileParser;
        this.inputStrategy = inputStrategy;
        this.outputStrategy = outputStrategy;
        this.sortingStrategy = sortingStrategy;
        this.filterWords = filterWords;
        this.trivialWords = trivialWords;
        this.keyword = keyword;

        this.loadFile(filename);
    }

    public String getCommand() {
        return inputStrategy.getCommand();
    }

    public int loadFile(String filename) {
        fileParser.setFilePath(filename);
        
        try {
            this.lines = fileParser.getSentencesAsLines();
            return 0;
        } catch (Exception e) {
            outputStrategy.display("Error reading file: " + e.getMessage());
            return 1;
        }
    
    }

    private AbstractSentencesProcessor getProcessorFromCommand(String command) {
        AbstractSentencesProcessor processor;
        if (Commands.KWIC.equals(command)) {
                processor = new KWICProcessor(this.lines, this.filterWords, this.trivialWords, this.sortingStrategy);
        } else if (command.startsWith(Commands.KEYWORD_SEARCH)) {
            String commandKeyword = command.substring(Commands.KEYWORD_SEARCH.length()).trim();
            if (commandKeyword.isEmpty()) {
                outputStrategy.display("Keyword search requires a keyword.");
                return null;
            }
            processor = new KeywordSearch(this.lines, this.filterWords, this.trivialWords, this.sortingStrategy, commandKeyword);
        } else if (Commands.INDEX_GENERATION.equals(command)) {
            processor = new IndexGeneration(this.lines, this.filterWords, this.trivialWords, this.sortingStrategy);
        } else {
            outputStrategy.display("Invalid command.");
            return null;
        }
        return processor;
    }
    
    public void run() {
        if (this.lines == null || this.lines.isEmpty()) {
            outputStrategy.display("No sentences to process.");
            return;
        }

        inputStrategy.open();
        while (true) {
            String command = this.getCommand();
            if (command == null) {
                break;
            }
            AbstractSentencesProcessor processor = this.getProcessorFromCommand(command);
            if (processor == null) {
                continue;
            }
            
            List<String> output = processor.getProcessedOutput();
            outputStrategy.display(output);
        }
        inputStrategy.close();
    }
}