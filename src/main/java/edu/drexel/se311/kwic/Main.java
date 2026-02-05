package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.fileparsing.AbstractFileParser;
import edu.drexel.se311.kwic.io.*;
import edu.drexel.se311.kwic.sorting.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: (java exec) <kwic-processing|keyword-search|index-generation> <config-filename>");
            System.exit(1);
        }
        
        String filename = args[0];

        OptionReader.readOptions("config.properties");
        String inputFile = OptionReader.getString("InputFileName");
        String inputObjString = OptionReader.getString("Input");
        String outputObjString = OptionReader.getString("Output");
        String sortingType = OptionReader.getString("Order");
        String wordFiltering = OptionReader.getString("WordFiltering");
        String trivialWords = OptionReader.getString("TrivialWords");

        CommandsAsStringListInput cList = new CommandsAsStringListInput();
        cList.addCommand("kwic-processing");
        AbstractFileParser fileParser = (AbstractFileParser) OptionReader.getObjectFromKey(inputObjString);
        OutputStrategy outputStrategy = (OutputStrategy) OptionReader.getObjectFromKey(outputObjString);
        SortingStrategy sortingStrategy;
        if ("Ascending".equals(sortingType)) {
            sortingStrategy = new AlphabeticSorter();
        } else {
            System.err.println("Unsupported sorting order");
            System.exit(1);
            return;
        }

        boolean filterWords;
        if ("Yes".equals(wordFiltering)) {
            filterWords = true;
        } else if ("No".equals(wordFiltering)) {
            filterWords = false;
        } else {
            System.err.println("Unsupported word filtering choice");
            System.exit(1);
            return;
        }
        
        List<String> trivialWordsList = Arrays.asList(trivialWords.split(","));

        

        KWICDriver driver = new KWICDriver(fileParser, cList, outputStrategy, sortingStrategy, filterWords, trivialWordsList);
        int result = driver.loadFile(inputFile);
        if (result == 0) {
            driver.run();
        } else {
            System.err.println("Failed to load file.");
            System.exit(1);
        }
    }
}
