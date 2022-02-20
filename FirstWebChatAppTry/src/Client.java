
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class Client extends JFrame implements ActionListener, KeyListener {
    static final String iox = "IOException: ";
    String ip;
    int port;

    JButton senderButton = new JButton("Send");
    JButton exiterButton = new JButton("Exit");

    JTextArea textFieldArea = new JTextArea();
    JScrollPane scroll = new JScrollPane(textFieldArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JTextField messageTextField = new JTextField(10);

    Client(String ip, int port) {
        this.ip = ip;
        this.port = port;

        setTitle("Client");
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

    private final class Receive implements Runnable {
        private final BufferedReader in;
        private final PrintWriter out;
        private final Socket clientSocket;

        private Receive(BufferedReader in, PrintWriter out, Socket clientSocket) {
            this.in = in;
            this.out = out;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) // (msg = in.readLine()) != null
                    if (in.ready())
                        textFieldArea.append("Server: " + in.readLine() + "\n"); // ! This is where Exception happens

                textFieldArea.append("Server out of Service\n");
                out.close();
                clientSocket.close();
            } catch (IOException io) {
                // ! System.err.println(iox + io.getMessage());
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + ((clientSocket == null) ? 0 : clientSocket.hashCode());
            result = prime * result + ((in == null) ? 0 : in.hashCode());
            result = prime * result + ((out == null) ? 0 : out.hashCode());
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
            Receive other = (Receive) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            if (clientSocket == null) {
                if (other.clientSocket != null)
                    return false;
            } else if (!clientSocket.equals(other.clientSocket))
                return false;
            if (in == null) {
                if (other.in != null)
                    return false;
            } else if (!in.equals(other.in))
                return false;
            if (out == null) {
                if (other.out != null)
                    return false;
            } else if (!out.equals(other.out))
                return false;
            return true;
        }

        private Client getEnclosingInstance() {
            return Client.this;
        }

        @Override
        public String toString() {
            return "Receive [clientSocket=" + clientSocket + ", in=" + in + ", out=" + out + "]";
        }
    }

    void Sender(PrintWriter out) {
        String msg = messageTextField.getText();
        messageTextField.setText(null);
        if (msg.trim().isEmpty() || out == null)
            return;

        textFieldArea.append("You: " + msg + "\n");
        out.println(msg);
        out.flush();
    }

    transient Socket clientSocket;
    transient PrintWriter out;
    transient BufferedReader in;
    transient Thread receiver;

    void creater() {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            receiver = new Thread(new Receive(in, out, clientSocket));
            receiver.start();
        } catch (IOException io) {
            // ! System.err.println(iox + io.getMessage());
        }

    }

    void termination() {
        try {
            receiver.interrupt();
            // System.out.println(receive.isAlive());
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException io) {
            System.err.println(iox + io.getMessage());
        } finally {
            dispose();
        }
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
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == senderButton)
            Sender(out);
        else if (e.getSource() == exiterButton)
            termination();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientSocket == null) ? 0 : clientSocket.hashCode());
        result = prime * result + ((exiterButton == null) ? 0 : exiterButton.hashCode());
        result = prime * result + ((in == null) ? 0 : in.hashCode());
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + ((messageTextField == null) ? 0 : messageTextField.hashCode());
        result = prime * result + ((out == null) ? 0 : out.hashCode());
        result = prime * result + port;
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        result = prime * result + ((scroll == null) ? 0 : scroll.hashCode());
        result = prime * result + ((senderButton == null) ? 0 : senderButton.hashCode());
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
        Client other = (Client) obj;
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
        if (in == null) {
            if (other.in != null)
                return false;
        } else if (!in.equals(other.in))
            return false;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
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
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
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
        if (textFieldArea == null) {
            if (other.textFieldArea != null)
                return false;
        } else if (!textFieldArea.equals(other.textFieldArea))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client [clientSocket=" + clientSocket + ", exiterButton=" + exiterButton + ", in=" + in + ", ip=" + ip
                + ", messageTextField=" + messageTextField + ", out=" + out + ", port=" + port + ", receiver="
                + receiver + ", scroll=" + scroll + ", senderButton=" + senderButton + ", textFieldArea="
                + textFieldArea + "]";
    }
}
