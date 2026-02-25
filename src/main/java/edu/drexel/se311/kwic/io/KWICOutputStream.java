package edu.drexel.se311.kwic.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class KWICOutputStream extends OutputStrategy {
    private OutputStream outputStream;
    private BufferedWriter writer;

    public KWICOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));

    }
    @Override
    public void display(String outputString) {
        try {
            writer.write(outputString);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(List<String> outputStrings) {
        StringBuilder sb = new StringBuilder();

        for (String line : outputStrings) {
            sb.append(line).append(System.lineSeparator());
        }
        this.display(sb.toString());
    }
}