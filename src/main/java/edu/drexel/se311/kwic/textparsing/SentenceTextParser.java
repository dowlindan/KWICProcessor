package edu.drexel.se311.kwic.textparsing;

import edu.drexel.se311.kwic.line.Line;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class SentenceTextParser extends AbstractTextParser {
    @Override
    public List<String> parseSentencesAsList(String rawText) {
        ArrayList<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(rawText);

        int start = iterator.first();

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = rawText.substring(start, end).trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }
       
       return sentences;
    }

    @Override
    public List<Line> parseSentencesAsLines(String rawText) {
        ArrayList<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(rawText);

        int start = iterator.first();

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = rawText.substring(start, end).trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            lines.add(new Line(sentences.get(i), i + 1));
        }
       return lines;
    }
}