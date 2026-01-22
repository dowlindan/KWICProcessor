package edu.drexel.se311.kwic.io;

public class ConsoleOutput extends OutputStrategy {
    @Override
    public void display(String outputString) {
        System.out.println(outputString);
    }
}
