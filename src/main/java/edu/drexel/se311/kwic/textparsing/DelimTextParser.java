package edu.drexel.se311.kwic.textparsing;

import edu.drexel.se311.kwic.line.Line;
import java.util.ArrayList;
import java.util.List;

public class DelimTextParser extends AbstractTextParser {
    private String delim;

    public DelimTextParser(String delim) {
        this.delim = delim;
    }

    @Override
    public List<Line> parseSentencesAsLines(String rawText) {
        List<Line> linesList = new ArrayList<>();
        String[] lines = rawText.split(delim);
        for (int i = 0; i < lines.length; i++) {
            linesList.add(new Line(lines[i], i+1));
        }
        return linesList;
    }
}