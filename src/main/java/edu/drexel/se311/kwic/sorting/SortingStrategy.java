package edu.drexel.se311.kwic.sorting;

import java.util.Collection;
public abstract class SortingStrategy {
    public abstract Collection<String> sort(Collection<String> strings);
}