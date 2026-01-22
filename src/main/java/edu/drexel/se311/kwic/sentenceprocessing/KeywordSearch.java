package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.List;

public class KeywordSearch extends AbstractSentencesProcessor {
    public KeywordSearch(List<String> inputSentences, SortingStrategy sortingStrategy) {
        super(inputSentences, sortingStrategy);
    }

    @Override
    public List<String> getProcessedOutput() {
        // Implementation for KWIC processing would go here
        return this.inputSentences; // TODO: Placeholder return
    }
}