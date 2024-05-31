
package me.twisted.whitelist;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import me.twisted.whitelist.Main;

public class WindowFrame {
    public JFrame frame;
    public JPanel panel;

    public WindowFrame() {
        this.Initialize();
    }

    private void Initialize() {
    	Vector3f vector = new Vector3f(760, 415, 600);
        frame = new JFrame("Tournament Whitelister");
        frame.setDefaultCloseOperation(3);
        frame.setBounds((int)vector.getX(), (int)vector.getY(), (int)vector.getZ(), 300);
        frame.setVisible(true);
        panel = new JPanel();
        panel.setBounds(0, 0, 600, 300);
        panel.setBackground(new Color(44, 47, 51));
        panel.repaint();
        panel.setVisible(true);
        JLabel label = new JLabel();
        label.setText("UUID has been copied to your clipboard!");
        label.setForeground(Color.white);
        label.setBounds(3, 3, 100, 100);
        label.setVisible(true);
        panel.add(label);
        JLabel uuid = new JLabel();
        uuid.setText("Your (hashed) UUID: " + Main.uuid);
        uuid.setForeground(Color.white);
        uuid.setBounds(3, 3, 200, 100);
        uuid.setVisible(true);
        vector.refresh();
        panel.add(uuid);
        frame.getContentPane().add(panel);
    }
}
