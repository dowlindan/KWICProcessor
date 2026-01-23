package edu.drexel.se311.kwic.line;

public class Line {
    private String content;
    private int lineNumber;
    public Line(String content, int lineNumber) {
        this.content = content;
        this.lineNumber = lineNumber;
    }

    public String getContent() {
        return content;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}