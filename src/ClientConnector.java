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

public class ClientConnector extends JFrame implements ActionListener, KeyListener {

    JButton Confirmer = new JButton("Confirm");
    JButton Exiter = new JButton("Exit");

    JLabel PortLabel = new JLabel("Port:");
    JLabel IPLabel = new JLabel("IP:");

    JTextField PortField = new JTextField("5000");
    JTextField IPField = new JTextField("localhost");

    ClientConnector() {
        setTitle("ClientConnector");
        setSize(400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setAlwaysOnTop(false);
        setResizable(false);
        setVisible(true);

        Confirmer.addActionListener(this);
        Confirmer.setBounds(250, 30, 100, 50);
        add(Confirmer);

        Exiter.addActionListener(this);
        Exiter.setBounds(250, 90, 100, 50);
        add(Exiter);

        PortLabel.setBounds(30, 20, 50, 20);
        add(PortLabel);

        PortField.setBounds(30, 40, 200, 30);
        PortField.setToolTipText("Port of the Server you want to connect to");
        add(PortField);

        IPLabel.setBounds(30, 90, 50, 20);
        add(IPLabel);

        IPField.setBounds(30, 120, 200, 30);
        IPField.setToolTipText("IP of the Server you want to connect to");
        add(IPField);

        addKeyListener(this);
        Confirmer.addKeyListener(this); // To Prevent
        Exiter.addKeyListener(this); // Frame from
        PortField.addKeyListener(this); // not being
        IPField.addKeyListener(this); // Focused
    }

    public static void main(String[] args) {
        new ClientConnector();
    }

    public void Redirect() {
        try {
            String IP = IPField.getText();
            int port = Integer.parseInt(PortField.getText());

            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(null, "Port must be between 0 and 65535", "PortError",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            new Client(IP, port).creater();
        } catch (NumberFormatException NFE) {
            JOptionPane.showMessageDialog(null, "NumberFormatException: " + NFE.getMessage(), "NFE",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Exiter)
            dispose();
        else if (e.getSource() == Confirmer)
            Redirect();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Redirect();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
