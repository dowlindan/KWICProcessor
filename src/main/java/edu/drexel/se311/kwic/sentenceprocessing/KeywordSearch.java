package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.ArrayList;
import java.util.List;

public class KeywordSearch extends AbstractSentencesProcessor {
    public KeywordSearch(List<Line> inputLines, SortingStrategy sortingStrategy) {
        super(inputLines, sortingStrategy);
    }

    @Override
    public List<String> getProcessedOutput() {
        List<String> processedOutput = new ArrayList<>();
        String keyword = this.inputLines.get(0).getContent();
        int sentencesWithKeyword = 0;
        for (int i = 1; i < this.inputLines.size(); i++) {
            if (this.inputLines.get(i).getContent().contains(keyword)) {
                sentencesWithKeyword++;
                String outputLine = "";
                for (String word : this.inputLines.get(i).getContent().split(" ")) {
                    if (word.equalsIgnoreCase(keyword)) {
                        outputLine += "\033[1m*" + word + "*\033[0m ";
                    } else {
                        outputLine += word + " ";
                    }
                }
                processedOutput.add(sentencesWithKeyword + " " + outputLine.trim());
            }
        }
        processedOutput.add(0, sentencesWithKeyword + " sentence(s) found containing the keyword: " + keyword);
        return processedOutput;
    }
}