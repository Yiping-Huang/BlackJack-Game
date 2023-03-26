package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for new-round action.

import ui.customstyle.CustomJButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewRoundJButton extends JButton implements ActionListener {

    private static String status = "Showing";
    private static int counter = 0;
    private final Timer timer = new Timer(10, this);

    // EFFECTS: construct a new-round button
    public NewRoundJButton(int corX, int corY) {
        setText("New Round");
        setEnabled(true);
        setBounds(corX, corY, 200, 50);
        setLocation(corX, corY);
        setUI(new CustomJButtonUI(Color.ORANGE));
        setSize(200, 50);
        setBackground(new Color(51, 153, 51));
        Font font = new Font("Time New Roman", Font.BOLD, 20);
        setFont(font);
        setVisible(false);
        timer.start();
    }

    public void setStatus(String s) {
        status = s;
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        } else if (counter >= 0 && 80 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 80 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        } else if (status.equals("Raise")) {
            setVisible(true);
            repaint();
        }
    }
}
