package edu.drexel.se311.kwic;

import edu.drexel.se311.kwic.io.Commands;
import edu.drexel.se311.kwic.io.ConsoleInput;
import edu.drexel.se311.kwic.io.InputStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class KWICClient {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final int TIMEOUT_MS = 5000;

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    public static void main(String[] args) {
        KWICClient client = new KWICClient();
        client.connect();
        client.runClientLoop();
    }

    public KWICClient() {

    }

    public void connect() {
        try {
            socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
            socket.setSoTimeout(TIMEOUT_MS);

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void runClientLoop() {
        InputStrategy input = new ConsoleInput();
        input.open();
        while (true) { 
            String line = input.getCommand();
            if (line == null || line.equals("exit")) {
                break;
            }
            if (line.trim().isEmpty()) {
                continue;
            }
            if (!line.startsWith(Commands.KEYWORD_SEARCH)) {
                System.out.println("Invalid command. Please enter a valid command.");
                continue;
            }
            try {
                writer.write(line);
                writer.newLine();
                writer.flush();

                String response = reader.readLine();
                System.out.println("Response from server: " + response);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        input.close();
    }
}