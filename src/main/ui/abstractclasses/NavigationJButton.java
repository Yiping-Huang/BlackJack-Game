package ui.abstractclasses;

import ui.customstyle.CustomJButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class NavigationJButton extends JButton implements ActionListener {

    private int corY;
    private final int iniCorY;
    private String status = "Hiding";
    private final Timer timer = new Timer(10,this);

    public NavigationJButton(int corX, int corY, int iniCorY) {
        this.corY = corY;
        this.iniCorY = iniCorY;
        setText("Navigation Button");
        setEnabled(true);
        setBounds(corX, this.corY,500,50);
        setLocation(corX, this.corY);
        setUI(new CustomJButtonUI(Color.ORANGE));
        setSize(500,50);
        setBackground(Color.WHITE);
        Font font = new Font("Time New Roman", Font.BOLD,20);
        setFont(font);
        setVisible(false);
        timer.start();
    }

    public void setCorY(int corY) {
        this.corY = corY;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int velY = 32;
        if (corY > iniCorY && corY <= (iniCorY + 800) && status.equals("Moving Up")) {
            setVisible(true);
            setLocation(getX(), corY -= velY);
            repaint();
        } else if (corY >= iniCorY && corY < (iniCorY + 800) && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        }
    }
}
