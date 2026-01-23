package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexGeneration extends AbstractSentencesProcessor {
    private Map<String, List<Integer>> indexMap;
    public IndexGeneration(List<Line> inputLines, SortingStrategy sortingStrategy) {
        super(inputLines, sortingStrategy);
        this.indexMap = new HashMap<>();
    }

    @Override
    public List<String> getProcessedOutput() {
        List<String> processedOutput = new ArrayList<>();
        for (Line line : this.inputLines) {
            String[] words = line.getContent().split(" ");
            for (String word : words) {
                if (!indexMap.containsKey(word)) {
                    indexMap.put(word, new ArrayList<>());
                }
                if (!indexMap.get(word).contains(line.getLineNumber())) {
                    indexMap.get(word).add(line.getLineNumber());
                }
            }
        }
        List<String> sortedKeys = this.sortingStrategy.sort(new ArrayList<>(indexMap.keySet()));
        for (String word : sortedKeys) {
            String lineNumbers = indexMap.get(word).toString().replaceAll("[\\[\\]]", "");
            processedOutput.add(word + ", " + lineNumbers);
        }
        return processedOutput;
    }
}