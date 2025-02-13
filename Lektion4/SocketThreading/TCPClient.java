package SocketThreading;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class TCPClient {

    public static void main(String[] args) throws Exception {
        clearTerm();

        String response;
        String myName = "Mads Franzen";
        String ipToConnect = "localhost";

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket(ipToConnect, 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outToServer.writeBytes("Hello;" + myName + '\n');
        System.out.println("Attempting to connect to " + ipToConnect + " - Awaiting response...");

        response = inFromServer.readLine();
        clearTerm();

        if (response.equals("JA")) {
            System.out.println("CONNECTION ESTABLISHED.");
            System.out.println("Type a message: ");
            chat(inFromUser, inFromServer, outToServer, clientSocket);
        } else {
            System.out.println("CONNECTION REJECTED.");
            clientSocket.close();
        }

    }

    private static void chat(BufferedReader inFromUser, BufferedReader inFromServer, DataOutputStream outToServer,
                             Socket clientSocket) {

        System.out.println();

        AtomicReference<Thread> threadReadRef = new AtomicReference<>();

        Thread threadWrite = new Thread(() -> {
            try {
                String message;
                while (true) {
                    message = inFromUser.readLine();
                    if (message == null || message.equals("END")) {
                        outToServer.writeBytes(message + '\n');
                        break; // Exit loop when user types "END"
                    }
                    outToServer.writeBytes(message + '\n');
                    System.out.println();
                }

                System.out.println("Ending chat...");

                // Interrupt threadRead
                Thread threadRead = threadReadRef.get();
                if (threadRead != null) {
                    threadRead.interrupt();
                }

                // Close the socket
                clientSocket.close();
                System.out.println("Chat has ended.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread threadRead = new Thread(() -> {
            try {
                String response;
                while (!Thread.currentThread().isInterrupted()) { // Stop when interrupted
                    response = inFromServer.readLine();
                    if (response == null) {
                        break; // Exit loop if the server closes the connection
                    }
                    System.out.print(" -> Server: ");
                    typewriterPrint(response, 50);
                }
            } catch (IOException e) {
                if (!Thread.currentThread().isInterrupted()) { // Ignore errors if interrupted
                    e.printStackTrace();
                }
            }
            System.out.println("Server connection closed.");
        });

        // Store threadRead in AtomicReference so threadWrite can access it
        threadReadRef.set(threadRead);

        threadWrite.start();
        threadRead.start();
    }

    public static void typewriterPrint(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
        System.out.println();
    }

    private static void clearTerm() {
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}