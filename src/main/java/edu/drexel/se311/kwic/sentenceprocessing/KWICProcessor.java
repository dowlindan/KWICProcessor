package edu.drexel.se311.kwic.sentenceprocessing;

import edu.drexel.se311.kwic.line.Line;
import edu.drexel.se311.kwic.sorting.SortingStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KWICProcessor extends AbstractSentencesProcessor {
    private List<LinkedList<String>> circularShifts;
    private final static String DELIMETER = " ";
    private final static String HEADER = "Index | Circular Shifted Lines | Original Line Index";

    public KWICProcessor(List<Line> inputLines, boolean filterWords, Set<String> trivialWords, SortingStrategy sortingStrategy) {
        super(inputLines, filterWords, trivialWords, sortingStrategy);
        circularShifts = new LinkedList<>();
    }

    @Override
    public List<String> getProcessedOutput() {
        List<String> processedOutput = new ArrayList<>();
        List<Line> shiftedLines = new ArrayList<>();

        for (Line line : this.inputLines) {
            String[] words = line.getContent().split(DELIMETER);
            LinkedList<String> wordsLinkedList = new LinkedList<>(Arrays.asList(words));

            List<Line> circularShiftsForLine = new ArrayList<>();

            circularShifts.add(new LinkedList<>(wordsLinkedList));
            circularShiftsForLine.add(new Line(String.join(" ", wordsLinkedList), line.getLineNumber()));

            for (int i = 0; i < words.length-1; i++) {
                String firstWord = wordsLinkedList.removeFirst();
                wordsLinkedList.addLast(firstWord);
                circularShiftsForLine.add(new Line(String.join(" ", wordsLinkedList), line.getLineNumber()));
            }
            shiftedLines.addAll(circularShiftsForLine);
        }
        shiftedLines = sortingStrategy.sortLines(shiftedLines);
        for (int i = 0; i < shiftedLines.size(); i++) {
            processedOutput.add(i+1 + " | " + shiftedLines.get(i).getContent() + " | " + shiftedLines.get(i).getLineNumber());
        }
        processedOutput.add(0, HEADER);
        return processedOutput;
    }
}