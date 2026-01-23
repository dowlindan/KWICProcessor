package edu.drexel.se311.kwic.fileparsing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.textparsing.AbstractTextParser;
import java.io.IOException;
import java.util.List;

public abstract class AbstractFileParser {
    protected String filePath;
    protected AbstractTextParser textParser;

    public AbstractFileParser(AbstractTextParser textParser) {
        this.textParser = textParser;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public abstract List<String> getSentencesAsList() throws IOException;

    public abstract List<Line> getSentencesAsLines() throws IOException;
}