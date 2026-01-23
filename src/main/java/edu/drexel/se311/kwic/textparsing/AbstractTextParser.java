package edu.drexel.se311.kwic.textparsing;

import edu.drexel.se311.kwic.line.Line;
import java.util.List;

public abstract class AbstractTextParser {
    public abstract List<String> parseSentencesAsList(String rawText);

    public abstract List<Line> parseSentencesAsLines(String rawText);
}