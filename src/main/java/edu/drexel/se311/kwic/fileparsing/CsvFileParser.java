package edu.drexel.se311.kwic.fileparsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.textparsing.DelimTextParser;

public class CsvFileParser extends AbstractFileParser {
    private static final String DELIM = "\\.";

    public CsvFileParser() {
        super(new DelimTextParser(DELIM));
    }

    @Override
    public List<Line> getSentencesAsLines() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath))).replace("\n", "");
        return textParser.parseSentencesAsLines(content);
    }   
}


