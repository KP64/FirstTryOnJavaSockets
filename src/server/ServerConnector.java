package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ServerConnector extends JFrame implements ActionListener, KeyListener {

    JButton confirmButton = new JButton("Confirm");
    JButton exitButton = new JButton("Exit");

    JLabel portLabel = new JLabel("Port:");

    JTextField portField = new JTextField("5000");

    ServerConnector() {
        setTitle("ServerConnector");
        setSize(400, 160);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setAlwaysOnTop(false);
        setResizable(false);
        setVisible(true);

        confirmButton.addActionListener(this);
        confirmButton.setBounds(250, 0, 100, 50);
        add(confirmButton);

        exitButton.addActionListener(this);
        exitButton.setBounds(250, 60, 100, 50);
        add(exitButton);

        portLabel.setBounds(30, 20, 50, 20);
        add(portLabel);

        portField.setBounds(30, 40, 200, 30);
        portField.setToolTipText("Port of your Server");
        add(portField);

        addKeyListener(this);
        confirmButton.addKeyListener(this); // To Prevent
        exitButton.addKeyListener(this); // Frame not being
        portField.addKeyListener(this); // Focused
    }

    public static void main(String[] args) {
        new ServerConnector();
    }

    public void redirect() {
        try {
            int port = Integer.parseInt(portField.getText());

            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(null, "Port must be between 0 and 65535", "PortError",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            new Server(port).creater();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "NumberFormatException: " + nfe.getMessage(), "nfe",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton)
            dispose();
        else if (e.getSource() == confirmButton)
            redirect();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            redirect();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
