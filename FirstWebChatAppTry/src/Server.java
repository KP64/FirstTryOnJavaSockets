import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class Server extends JFrame implements ActionListener, KeyListener {
    static final String iox = "IOException: ";

    int port;

    JButton senderButton = new JButton("Send");
    JButton exiterButton = new JButton("Exit");

    JTextArea textFieldArea = new JTextArea();
    JScrollPane scroll = new JScrollPane(textFieldArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JTextField messageTextField = new JTextField(10);

    Server(int port) {
        this.port = port;

        setTitle("Server");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setAlwaysOnTop(false);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        textFieldArea.setEditable(false);

        scroll.setBounds(0, 0, 500, 300);
        add(scroll);

        senderButton.setBounds(550, 40, 100, 100);
        senderButton.addActionListener(this);
        add(senderButton);

        exiterButton.setBounds(550, 160, 100, 100);
        exiterButton.addActionListener(this);
        add(exiterButton);

        messageTextField.setBounds(0, 300, 400, 40);
        add(messageTextField);

        addKeyListener(this);
        textFieldArea.addKeyListener(this);
        scroll.addKeyListener(this);
        senderButton.addKeyListener(this);
        exiterButton.addKeyListener(this);
        messageTextField.addKeyListener(this);
    }

    transient ServerSocket serverSocket;
    transient Socket clientSocket;
    transient BufferedReader reader;
    transient PrintWriter out;
    transient Thread receive;

    void creater() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());

            receive = new Thread(new Receiver());
            receive.start();
        } catch (IOException io) {
            // ! System.err.println(iox + io.getMessage());
        }
    }

    void termination() {
        try {
            receive.interrupt(); // ! This in combination with line 109 works
            // System.out.println(receive.isAlive());
            reader.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException io) {
            // ! System.err.println(iox + io.getMessage());
        } finally {
            dispose();
        }
    }

    private final class Receiver implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted())
                    if (reader.ready())
                        textFieldArea.append("Client: " + reader.readLine() + "\n"); // ! Exception happens here

                textFieldArea.append("Client Disconnected");
            } catch (IOException e) {
                // ! System.err.println(iox + e.getMessage());
            }
        }

        @Override
        public String toString() {
            return "Receiver []";
        }
    }

    void Sender(PrintWriter out) {
        String msg = messageTextField.getText();
        messageTextField.setText(null);
        if (msg.trim().isEmpty())
            return;

        textFieldArea.append("You: " + msg + "\n");
        out.println(msg);
        out.flush();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == senderButton)
            Sender(out);
        else if (e.getSource() == exiterButton)
            termination();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Sender(out);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            termination();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientSocket == null) ? 0 : clientSocket.hashCode());
        result = prime * result + ((exiterButton == null) ? 0 : exiterButton.hashCode());
        result = prime * result + ((messageTextField == null) ? 0 : messageTextField.hashCode());
        result = prime * result + ((out == null) ? 0 : out.hashCode());
        result = prime * result + port;
        result = prime * result + ((reader == null) ? 0 : reader.hashCode());
        result = prime * result + ((receive == null) ? 0 : receive.hashCode());
        result = prime * result + ((scroll == null) ? 0 : scroll.hashCode());
        result = prime * result + ((senderButton == null) ? 0 : senderButton.hashCode());
        result = prime * result + ((serverSocket == null) ? 0 : serverSocket.hashCode());
        result = prime * result + ((textFieldArea == null) ? 0 : textFieldArea.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Server other = (Server) obj;
        if (clientSocket == null) {
            if (other.clientSocket != null)
                return false;
        } else if (!clientSocket.equals(other.clientSocket))
            return false;
        if (exiterButton == null) {
            if (other.exiterButton != null)
                return false;
        } else if (!exiterButton.equals(other.exiterButton))
            return false;
        if (messageTextField == null) {
            if (other.messageTextField != null)
                return false;
        } else if (!messageTextField.equals(other.messageTextField))
            return false;
        if (out == null) {
            if (other.out != null)
                return false;
        } else if (!out.equals(other.out))
            return false;
        if (port != other.port)
            return false;
        if (reader == null) {
            if (other.reader != null)
                return false;
        } else if (!reader.equals(other.reader))
            return false;
        if (receive == null) {
            if (other.receive != null)
                return false;
        } else if (!receive.equals(other.receive))
            return false;
        if (scroll == null) {
            if (other.scroll != null)
                return false;
        } else if (!scroll.equals(other.scroll))
            return false;
        if (senderButton == null) {
            if (other.senderButton != null)
                return false;
        } else if (!senderButton.equals(other.senderButton))
            return false;
        if (serverSocket == null) {
            if (other.serverSocket != null)
                return false;
        } else if (!serverSocket.equals(other.serverSocket))
            return false;
        if (textFieldArea == null) {
            if (other.textFieldArea != null)
                return false;
        } else if (!textFieldArea.equals(other.textFieldArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Server [clientSocket=" + clientSocket + ", exiterButton=" + exiterButton + ", messageTextField="
                + messageTextField + ", out=" + out + ", port=" + port + ", reader=" + reader + ", receive=" + receive
                + ", scroll=" + scroll + ", senderButton=" + senderButton + ", serverSocket=" + serverSocket
                + ", textFieldArea=" + textFieldArea + "]";
    }
}