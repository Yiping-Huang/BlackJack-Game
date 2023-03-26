package ui.abstractclasses;

import ui.customstyle.CustomJButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OperationJButton extends JButton implements ActionListener {

    private String status = "Hiding";
    private int counter = 0;
    private final Timer timer = new Timer(10, this);

    public OperationJButton(int corX, int corY) {
        setText("Operation Button");
        setEnabled(true);
        setBounds(corX,corY,200,50);
        setLocation(corX, corY);
        setUI(new CustomJButtonUI(Color.ORANGE));
        setSize(200,50);
        setBackground(new Color(51,153,51));
        Font font = new Font("Time New Roman", Font.BOLD,20);
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
        } else if (counter >= 0 && 40 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 40 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        } else if (status.equals("Raise")) {
            setVisible(true);
            repaint();
        }
    }
}
