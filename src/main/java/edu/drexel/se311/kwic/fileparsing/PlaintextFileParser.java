package edu.drexel.se311.kwic.fileparsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class PlaintextFileParser extends AbstractFileParser {
    @Override
    public List<String> getSentencesAsList() throws IOException {
       String content = new String(Files.readAllBytes(Paths.get(filePath)));
       List<String> sentences = new ArrayList<>();

       BreakIterator iterator = BreakIterator.getSentenceInstance();
       iterator.setText(content);

       int start = iterator.first();

       for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
           String sentence = content.substring(start, end).trim();
           if (!sentence.isEmpty()) {
               sentences.add(sentence);
           }
       }
       
       return sentences;
    }
}

