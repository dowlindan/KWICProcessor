package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.io.*;

public class Main {
    public static void main(String[] args) {
        // if (args.length < 1) {
        //     System.err.println("Usage: java Main <filename>");
        //     System.exit(1);
        // }
        
        // String filename = args[0];

        OptionReader.readOptions();
        String inputObjString = OptionReader.getString("Input");
        InputStrategy inputStrategy = (InputStrategy) OptionReader.getObjectFromStr(inputObjString);

        // InputStrategy input = new ConsoleInput();
        // OutputStrategy output = new ConsoleOutput();

        // KWICDriver driver = new KWICDriver(input, output);
        // int result = driver.loadFile(filename);
        // if (result == 0) {
        //     driver.run();
        // } else {
        //     System.err.println("Failed to load file.");
        //     System.exit(1);
        // }
    }
}
