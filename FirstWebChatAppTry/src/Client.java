
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.Socket;
    import java.util.Scanner;
    
    public class Client {
        public static void main(String[] args) {
            try {
                Socket clientSocket = new Socket("localhost", 5000); // ! IP, Port
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream()); // ? Output Printer (Server)
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // ? ReadInput
    
                // ? New Thread
                Thread sender = new Thread(new Runnable() { // ? Anonymus (new) Runnable
                    String msg;
    
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
                });
                sender.start(); // ? Start Thread
    
                Thread receiver = new Thread(new Runnable() {
                    String msg;
    
                    @Override
                    public void run() {
                        try {
                            while ((msg = in.readLine()) != null)
                                System.out.printf("Server: %s %n", msg);
    
                            System.out.println("Server out of Service");
                            out.close();
                            clientSocket.close();
                        } catch (IOException io) {
                            System.out.println("IOException: " + io.getMessage());
                        }
                    }
                });
                receiver.start();
            } catch (IOException io) {
                System.out.println("IOException: " + io.getMessage());
            }
        }
    } 
