package edu.drexel.se311.kwic.io;

import java.util.List;

public abstract class OutputStrategy {
    public abstract void display(String outputString);

    public void display(List<String> outputStrings) {
        for (String line : outputStrings) {
            this.display(line);
        }
    }
}
