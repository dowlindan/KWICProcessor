package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.List;
import java.util.Set;

public abstract class AbstractSentencesProcessor {
    protected List<Line> inputLines;
    protected Set<String> trivialWords;
    protected SortingStrategy sortingStrategy;
    protected boolean filterWords;

    public AbstractSentencesProcessor(List<Line> inputLines, boolean filterWords, Set<String> trivialWords, SortingStrategy sortingStrategy) {
        this.inputLines = inputLines;
        this.filterWords = filterWords;
        this.trivialWords = trivialWords;
        this.sortingStrategy = sortingStrategy;
    }

    protected boolean isWordTrivial(String word) {
        return this.filterWords && trivialWords.contains(word);
    }

    public abstract List<String> getProcessedOutput();
}