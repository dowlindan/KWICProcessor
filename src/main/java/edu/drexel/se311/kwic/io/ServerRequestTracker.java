package edu.drexel.se311.kwic.io;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerRequestTracker {
    private static ServerRequestTracker instance;
    private final AtomicInteger SuccessfulSearches = new AtomicInteger(0);
    private final AtomicInteger totalSearches = new AtomicInteger(0);

    private ServerRequestTracker() {}

    public static synchronized ServerRequestTracker getInstance() {
        if (instance == null) {
            instance = new ServerRequestTracker();
        }
        return instance;
    }

    public void incrementSuccessfulSearches() {
        SuccessfulSearches.incrementAndGet();
    }
    
    public int getSuccessfulSearches() {
        return SuccessfulSearches.get();
    }

    public void incrementTotalSearches() {
        totalSearches.incrementAndGet();
    }
    
    public int getTotalSearches() {
        return totalSearches.get();
    }
}