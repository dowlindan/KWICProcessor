package edu.drexel.se311.kwic.fileparsing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.textparsing.AbstractTextParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PlaintextFileParser extends AbstractFileParser {
    public PlaintextFileParser() {}
    
    public PlaintextFileParser(AbstractTextParser textParser) {
        super(textParser);
    }

    @Override
    public List<Line> getSentencesAsLines() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return textParser.parseSentencesAsLines(content);
    }   
}

