package edu.drexel.se311.kwic.sorting;

import edu.drexel.se311.kwic.line.Line;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlphabeticSorter extends SortingStrategy {
    @Override
    public List<String> sort(List<String> strings) {
        List<String> sortedLines = new ArrayList<>(strings);
        Collections.sort(sortedLines, (String o1, String o2) -> {
            int caseInsensitiveCompare = o1.toLowerCase().compareTo(o2.toLowerCase());
            if (caseInsensitiveCompare != 0) {
                return caseInsensitiveCompare;
            }
            return -o1.compareTo(o2);
        });
        return sortedLines;
    }

    @Override
    public List<Line> sortLines(List<Line> lines) {
        List<Line> sortedLines = new ArrayList<>(lines);
        Collections.sort(sortedLines, (Line o1, Line o2) -> {
            int caseInsensitiveCompare = o1.getContent().toLowerCase().compareTo(o2.getContent().toLowerCase());
            if (caseInsensitiveCompare != 0) {
                return caseInsensitiveCompare;
            }
            return -o1.getContent().compareTo(o2.getContent());
        });
        return sortedLines;
    }
}