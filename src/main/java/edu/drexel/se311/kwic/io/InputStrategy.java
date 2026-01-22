package edu.drexel.se311.kwic.io;

public abstract class InputStrategy {
    public void open() {}
    public void close() {}
    public abstract String getCommand();
}
