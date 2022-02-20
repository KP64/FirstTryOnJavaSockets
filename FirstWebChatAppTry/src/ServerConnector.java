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

    JButton Confirmer = new JButton("Confirm");
    JButton Exiter = new JButton("Exit");

    JLabel PortLabel = new JLabel("Port:");

    JTextField PortField = new JTextField("5000");

    ServerConnector() {
        setTitle("ServerConnector");
        setSize(400, 160);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setAlwaysOnTop(false);
        setResizable(false);
        setVisible(true);

        Confirmer.addActionListener(this);
        Confirmer.setBounds(250, 0, 100, 50);
        add(Confirmer);

        Exiter.addActionListener(this);
        Exiter.setBounds(250, 60, 100, 50);
        add(Exiter);

        PortLabel.setBounds(30, 20, 50, 20);
        add(PortLabel);

        PortField.setBounds(30, 40, 200, 30);
        PortField.setToolTipText("Port of your Server");
        add(PortField);

        addKeyListener(this);
        Confirmer.addKeyListener(this); // To Prevent
        Exiter.addKeyListener(this); // Frame not being
        PortField.addKeyListener(this); // Focused
    }

    public static void main(String[] args) {
        new ServerConnector();
    }

    public void Redirect() {
        try {
            int port = Integer.parseInt(PortField.getText());

            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(null, "Port must be between 0 and 65535", "PortError",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            new Server(port).creater();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Confirmer == null) ? 0 : Confirmer.hashCode());
        result = prime * result + ((Exiter == null) ? 0 : Exiter.hashCode());
        result = prime * result + ((PortField == null) ? 0 : PortField.hashCode());
        result = prime * result + ((PortLabel == null) ? 0 : PortLabel.hashCode());
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
        ServerConnector other = (ServerConnector) obj;
        if (Confirmer == null) {
            if (other.Confirmer != null)
                return false;
        } else if (!Confirmer.equals(other.Confirmer))
            return false;
        if (Exiter == null) {
            if (other.Exiter != null)
                return false;
        } else if (!Exiter.equals(other.Exiter))
            return false;
        if (PortField == null) {
            if (other.PortField != null)
                return false;
        } else if (!PortField.equals(other.PortField))
            return false;
        if (PortLabel == null) {
            if (other.PortLabel != null)
                return false;
        } else if (!PortLabel.equals(other.PortLabel))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServerConnector [Confirmer=" + Confirmer + ", Exiter=" + Exiter + ", PortField=" + PortField
                + ", PortLabel=" + PortLabel + "]";
    }

}
