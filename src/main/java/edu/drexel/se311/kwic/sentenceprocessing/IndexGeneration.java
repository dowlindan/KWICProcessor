package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.List;

public class IndexGeneration extends AbstractSentencesProcessor {
    public IndexGeneration(List<String> inputSentences, SortingStrategy sortingStrategy) {
        super(inputSentences, sortingStrategy);
    }

    @Override
    public List<String> getProcessedOutput() {
        // Implementation for KWIC processing would go here
        return this.inputSentences; // TODO: Placeholder return
    }
}