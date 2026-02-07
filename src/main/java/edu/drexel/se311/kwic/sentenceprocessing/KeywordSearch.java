package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KeywordSearch extends AbstractSentencesProcessor {
    private String keyword;

    public KeywordSearch(List<Line> inputLines, boolean filterWords, Set<String> trivialWords, SortingStrategy sortingStrategy, String keyword) {
        super(inputLines, filterWords, trivialWords, sortingStrategy);
        this.keyword = keyword;
    }

    @Override
    public List<String> getProcessedOutput() {
        List<String> processedOutput = new ArrayList<>();
        int sentencesWithKeyword = 0;
        for (int i = 0; i < this.inputLines.size(); i++) {
            if (this.inputLines.get(i).getContent().contains(keyword)) {
                sentencesWithKeyword++;
                String outputLine = "";
                for (String word : this.inputLines.get(i).getContent().split(" ")) {
                    if (word.contains(keyword)) {
                        outputLine += "\033[1m*" + word + "*\033[0m ";
                    } else {
                        outputLine += word + " ";
                    }
                }
                processedOutput.add(sentencesWithKeyword + " " + outputLine);
            }
        }
        processedOutput.add(0, sentencesWithKeyword + " sentence(s) found containing the keyword: " + keyword);
        return processedOutput;
    }
}