package edu.drexel.se311.kwic.io;

import java.util.List;

/**
 * Encapsulates and formats a response message from the KWICServer.
 * The first line is message length, the rest is content.
 */
public class KWICProtocolMessage {
    private List<String> messages;
    private int messageLines;
    
    public KWICProtocolMessage(List<String> messages) {
        this.messages = messages;
        this.messageLines = messages.size();
    }

    public String toMessageString() {
        StringBuilder sb = new StringBuilder();

        sb.append(messageLines).append(System.lineSeparator());
        for (String line : messages) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public int getMessageLines() {
        return messageLines;
    }

    public void setMessageLines(int messageLines) {
        this.messageLines = messageLines;
    }
}