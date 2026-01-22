package edu.drexel.se311.kwic.io;

import java.util.Scanner;

public class ConsoleInput extends InputStrategy {
    @Override
    public String getCommand() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        sc.close();
        return userInput;
    }
}