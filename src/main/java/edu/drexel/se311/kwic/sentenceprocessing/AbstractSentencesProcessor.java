package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.List;

public abstract class AbstractSentencesProcessor {
    // protected List<String> inputSentences;
    protected List<Line> inputLines;
    protected SortingStrategy sortingStrategy;

    public AbstractSentencesProcessor(List<Line> inputLines, SortingStrategy sortingStrategy) {
        this.inputLines = inputLines;
        this.sortingStrategy = sortingStrategy;
    }

    public abstract List<String> getProcessedOutput();
}