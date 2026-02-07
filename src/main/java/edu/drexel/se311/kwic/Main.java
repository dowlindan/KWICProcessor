package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: (java exec) <kwic-processing|keyword-search|index-generation> <keyword if applicable> <config-filename>");
            System.exit(1);
        }
        
        String command = args[0];
        String keyword;
        String configFilename;
        if (Commands.KEYWORD_SEARCH.equals(command)) {
            if (args.length != 3) {
                System.err.println("Need 3 args for keyword: keyword <word> <config-file>");
                System.exit(1);
            }
            keyword = args[1];
            configFilename = args[2];
        } else {
            if (args.length != 2) {
                System.err.println("Need 2 args: <kwic-processing|index-generation> <config-filename>");
                System.exit(1);
            }
            keyword = null;
            configFilename = args[1];
        }
        
        KWICDriver driver = KWICDriver.fromConfig(command, keyword, configFilename);
        driver.run();
    }
}
