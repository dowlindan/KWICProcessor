package edu.drexel.se311.kwic.io;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CommandsAsStringListInput extends InputStrategy {
    private int index;
    private List<String> commands = new ArrayList<String>();

    public void addCommand(String command) {
        this.commands.add(command);
    }
    
    @Override
    public String getCommand() {
        if (this.index < this.commands.size()) {
            String command = this.commands.get(this.index);
            index++;
            return command;
        }
        return null;
    }
}