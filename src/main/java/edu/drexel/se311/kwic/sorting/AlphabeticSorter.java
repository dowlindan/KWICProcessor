package edu.drexel.se311.kwic.sorting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlphabeticSorter extends SortingStrategy {
    @Override
    public Collection<String> sort(Collection<String> strings) {
        List<String> sortedLines = new ArrayList<>(strings);
        Collections.sort(sortedLines);
        return sortedLines;
    }
}