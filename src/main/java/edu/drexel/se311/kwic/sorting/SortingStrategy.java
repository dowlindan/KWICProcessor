package edu.drexel.se311.kwic.sorting;

import edu.drexel.se311.kwic.line.Line;
import java.util.List;

public abstract class SortingStrategy {
    public abstract List<String> sort(List<String> strings);
    public abstract List<Line> sortLines(List<Line> lines);
}