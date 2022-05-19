package server;

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
    static final String IOX = "IOException: ";

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

        }
    }

    void termination() {
        try {
            clientSocket.close();

            serverSocket.close();

            reader.close();

            out.close();

            receive.interrupt();

        } catch (IOException io) {

        } finally {
            dispose();
        }
    }

    private final class Receiver implements Runnable {
        String msg;

        @Override
        public void run() {
            try {
                while (!receive.isInterrupted())
                    if ((msg = reader.readLine()) != null)
                        textFieldArea.append("Client: " + msg + "\n"); // ! Exception happens here
                    else
                        break;
                textFieldArea.append("Client Disconnected\n");
            } catch (IOException e) {

            }
        }
    }

    void sender(PrintWriter out) {
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
            sender(out);
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
            sender(out);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            termination();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}