
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final class Send implements Runnable {
        private final PrintWriter out;
        private final Socket clientSocket;
        String msg;

        private Send(PrintWriter out, Socket clientSocket) {
            this.out = out;
            this.clientSocket = clientSocket;
        }

        @Override // ! Override default Runnable
        public void run() { // ? Run the Runnable
            try (Scanner sc = new Scanner(System.in)) {
                while (clientSocket.isConnected()) { // ? If connected allow Input
                    msg = sc.nextLine(); // * Input
                    out.println(msg); // * Output
                    out.flush(); // ! Clears Printstream from leftover objects
                }
            }
        }
    }

    private static final class Receive implements Runnable {
        private final BufferedReader in;
        private final PrintWriter out;
        private final Socket clientSocket;
        String msg;

        private Receive(BufferedReader in, PrintWriter out, Socket clientSocket) {
            this.in = in;
            this.out = out;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                while ((msg = in.readLine()) != null)
                    System.out.printf("Server: %s %n", msg);

                System.out.println("Server out of Service");
                out.close();
                clientSocket.close();
            } catch (IOException io) {
                System.err.println("IOException: " + io.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 5000)) {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    Thread sender = new Thread(new Send(out, clientSocket));
                    sender.start();

                    Thread receiver = new Thread(new Receive(in, out, clientSocket));
                    receiver.start();

                    receiver.join();
                    sender.join();
                }
            }
        } catch (IOException io) {
            System.err.println("IOException: " + io.getMessage());
        } catch (InterruptedException e) {
            System.err.println("InterruptedException: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
