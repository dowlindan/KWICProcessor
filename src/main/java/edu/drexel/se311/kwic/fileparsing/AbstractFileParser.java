package edu.drexel.se311.kwic.fileparsing;

import java.io.IOException;
import java.util.List;

public abstract class AbstractFileParser {
    protected String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public abstract List<String> getSentencesAsList() throws IOException;

}