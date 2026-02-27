package edu.drexel.se311.kwic.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class KWICOutputStream extends OutputStrategy {
    private OutputStream outputStream;
    private BufferedWriter writer;
    private TxtOutput loggingOutput;
    private int successfulSearches;
    private int searchAttempts;

    public KWICOutputStream(OutputStream outputStream, String loggingFile) {
        this.outputStream = outputStream;
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        this.loggingOutput = new TxtOutput();
        loggingOutput.setOutputFilename(loggingFile);
        this.successfulSearches = 0;

    }
    @Override
    public void display(String outputString) {
        try {
            List<String> outputStrings = new ArrayList<>();
            outputStrings.add(outputString);
            KWICProtocolMessage message = new KWICProtocolMessage(outputStrings);

            writer.write(message.toMessageString());
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(List<String> outputStrings) {
        KWICProtocolMessage message = new KWICProtocolMessage(outputStrings);
        System.out.println("Sending response to client:\n" + message.toMessageString());
        logKeywordSearch(message);
        try {
            writer.write(message.toMessageString());
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logKeywordSearch(KWICProtocolMessage message) {
        ServerRequestTracker tracker = ServerRequestTracker.getInstance();
        tracker.incrementTotalSearches();
        if (message.getMessageLines() > 1) {
            tracker.incrementSuccessfulSearches();
        }
        loggingOutput.display("[" + Instant.now().toString() + "]" + " Total Searches (since server startup): " + tracker.getTotalSearches());
        loggingOutput.display("[" + Instant.now().toString() + "]" + " Successful Searches (since server startup): " + tracker.getSuccessfulSearches());

    }
}