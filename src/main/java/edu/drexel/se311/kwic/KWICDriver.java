package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.*;
import edu.drexel.se311.kwic.io.*;
import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sentenceprocessing.*;
import edu.drexel.se311.kwic.sorting.*;
import edu.drexel.se311.kwic.textparsing.NewlineTextParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KWICDriver {
    private AbstractFileParser fileParser;
    private InputStrategy inputStrategy;
    private OutputStrategy outputStrategy; 
    private SortingStrategy sortingStrategy;
    private boolean filterWords; // TODO
    private List<String> trivialWords; // TODO
    private String keyword;
    
    private List<Line> lines;

    public KWICDriver(String filename, AbstractFileParser fileParser, InputStrategy inputStrategy, OutputStrategy outputStrategy, SortingStrategy sortingStrategy, 
        boolean filterWords, List<String> trivialWords, String keyword) {
        this.fileParser = fileParser;
        this.inputStrategy = inputStrategy;
        this.outputStrategy = outputStrategy;
        this.sortingStrategy = sortingStrategy;
        this.filterWords = filterWords;
        this.trivialWords = trivialWords;
        this.keyword = keyword;

        this.loadFile(filename);
    }

    public static KWICDriver fromConfig(String command, String keyword, String configFilename) {
        OptionReader.readOptions(configFilename);
        String inputFile = OptionReader.getString("InputFileName");
        String inputObjString = OptionReader.getString("Input");
        String outputObjString = OptionReader.getString("Output");
        String sortingType = OptionReader.getString("Order");
        String wordFiltering = OptionReader.getString("WordFiltering");
        String trivialWords = OptionReader.getString("TrivialWords");

        CommandsAsStringListInput cList = new CommandsAsStringListInput();
        cList.addCommand("kwic-processing");
        AbstractFileParser fileParser = (AbstractFileParser) OptionReader.getObjectFromKey(inputObjString);
        fileParser.setTextParser(new NewlineTextParser());
        OutputStrategy outputStrategy = (OutputStrategy) OptionReader.getObjectFromKey(outputObjString);
        SortingStrategy sortingStrategy;
        if ("Ascending".equals(sortingType)) {
            sortingStrategy = new AlphabeticSorter();
        } else {
            System.err.println("Unsupported sorting order");
            System.exit(1);
            return null;
        }

        boolean filterWords;
        if ("Yes".equals(wordFiltering)) {
            filterWords = true;
        } else if ("No".equals(wordFiltering)) {
            filterWords = false;
        } else {
            System.err.println("Unsupported word filtering choice");
            System.exit(1);
            return null;
        }
        
        List<String> trivialWordsList = Arrays.asList(trivialWords.split(","));
        KWICDriver driver = new KWICDriver(inputFile, fileParser, cList, outputStrategy, sortingStrategy, filterWords, trivialWordsList, keyword);
        return driver;
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

    private void displayUsage() {
        outputStrategy.display("Usage: (java exec) <kwic-processing|keyword-search|index-generation> <config-filename>");
    }

    private AbstractSentencesProcessor getProcessorFromCommand(String command) {
        AbstractSentencesProcessor processor;
        if (Commands.KWIC.equals(command)) {
                processor = new KWICProcessor(this.lines, sortingStrategy);
        } else if (command.startsWith(Commands.KEYWORD_SEARCH)) {
            //String keyword = command.substring(Commands.KEYWORD_SEARCH.length()).trim();
            if (keyword.isEmpty()) {
                outputStrategy.display("Keyword search requires a keyword.");
                displayUsage();
                return null;
            }
            List<Line> linesWithKeyword = new ArrayList<>(this.lines);
            linesWithKeyword.add(0, new Line(keyword, -1)); // Add keyword as first line
            //  This is horrible design but I am out of time
            processor = new KeywordSearch(linesWithKeyword, sortingStrategy);
        } else if (Commands.INDEX_GENERATION.equals(command)) {
            processor = new IndexGeneration(this.lines, sortingStrategy);
        } else {
            outputStrategy.display("Invalid command.");
            displayUsage();
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
                break;
            }
            
            List<String> output = processor.getProcessedOutput();
            outputStrategy.display(output);
        }
        inputStrategy.close();
    }
}