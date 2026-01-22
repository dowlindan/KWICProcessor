package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.List;

public abstract class AbstractSentencesProcessor {
    protected List<String> inputSentences;
    protected SortingStrategy sortingStrategy;

    public AbstractSentencesProcessor(List<String> inputSentences, SortingStrategy sortingStrategy) {
        this.inputSentences = inputSentences;
        this.sortingStrategy = sortingStrategy;
    }

    public abstract List<String> getProcessedOutput();
}