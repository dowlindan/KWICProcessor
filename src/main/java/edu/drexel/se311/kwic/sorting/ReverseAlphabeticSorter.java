package edu.drexel.se311.kwic.sorting;

import edu.drexel.se311.kwic.line.Line;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseAlphabeticSorter extends SortingStrategy {
    public ReverseAlphabeticSorter() {}
    
    @Override
    public List<String> sort(List<String> strings) {
        List<String> sortedLines = new ArrayList<>(strings);
        Collections.sort(sortedLines, (String o1, String o2) -> {
            int caseInsensitiveCompare = o2.toLowerCase().compareTo(o1.toLowerCase());
            if (caseInsensitiveCompare != 0) {
                return caseInsensitiveCompare;
            }
            return -o2.compareTo(o1);
        });
        return sortedLines;
    }

    @Override
    public List<Line> sortLines(List<Line> lines) {
        List<Line> sortedLines = new ArrayList<>(lines);
        Collections.sort(sortedLines, (Line o1, Line o2) -> {
            int caseInsensitiveCompare = o2.getContent().toLowerCase().compareTo(o1.getContent().toLowerCase());
            if (caseInsensitiveCompare != 0) {
                return caseInsensitiveCompare;
            }
            return -o2.getContent().compareTo(o1.getContent());
        });
        return sortedLines;
    }
}