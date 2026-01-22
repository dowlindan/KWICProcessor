package edu.drexel.se311.kwic.io;

import java.util.Scanner;

public class ConsoleInput extends InputStrategy {
    private Scanner sc;
    public void open() {
        sc = new Scanner(System.in);
    }
    public void close() {
        if (sc != null) {
            sc.close();
        }
    }
    @Override
    public String getCommand() {
        String userInput = sc.nextLine();
        return userInput;
    }
}