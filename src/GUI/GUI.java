package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    public GUI() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.setLayout(null);

        JLabel label = new JLabel("User");
        label.setBounds(10,20,80,25);
        panel.add(label);

        JTextField userText = new JTextField();
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
