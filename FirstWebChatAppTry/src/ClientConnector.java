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
        PortField.setToolTipText("Port of your Server");
        add(PortField);

        IPLabel.setBounds(30, 90, 50, 20);
        add(IPLabel);

        IPField.setBounds(30, 120, 200, 30);
        IPField.setToolTipText("IP of your Server");
        add(IPField);

        addKeyListener(this);
        Confirmer.addKeyListener(this); // To Prevent
        Exiter.addKeyListener(this); // Frame not being
        PortField.addKeyListener(this); // Focused
        IPField.addKeyListener(this);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Confirmer == null) ? 0 : Confirmer.hashCode());
        result = prime * result + ((Exiter == null) ? 0 : Exiter.hashCode());
        result = prime * result + ((IPField == null) ? 0 : IPField.hashCode());
        result = prime * result + ((IPLabel == null) ? 0 : IPLabel.hashCode());
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
        ClientConnector other = (ClientConnector) obj;
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
        if (IPField == null) {
            if (other.IPField != null)
                return false;
        } else if (!IPField.equals(other.IPField))
            return false;
        if (IPLabel == null) {
            if (other.IPLabel != null)
                return false;
        } else if (!IPLabel.equals(other.IPLabel))
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
        return "ClientConnector [Confirmer=" + Confirmer + ", Exiter=" + Exiter + ", IPField=" + IPField + ", IPLabel="
                + IPLabel + ", PortField=" + PortField + ", PortLabel=" + PortLabel + "]";
    }
}
