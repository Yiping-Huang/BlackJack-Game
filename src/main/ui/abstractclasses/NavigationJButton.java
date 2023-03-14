package ui.abstractclasses;

import ui.customstyle.CustomJButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class NavigationJButton extends JButton implements ActionListener {

    private int corY;
    private final int iniCorY;
    private int velY = 10;
    private String status = "Hiding";

    public NavigationJButton(int corX, int corY, int iniCorY) {
        this.corY = corY;
        this.iniCorY = iniCorY;
        setText("Navigation Button");
        setEnabled(true);
        setBounds(corX,this.corY,500,50);
        setLocation(corX, this.corY);
        setUI(new CustomJButtonUI(Color.ORANGE));
        setSize(500,50);
        setBackground(Color.WHITE);
        Font font = new Font("Time New Roman", Font.BOLD,20);
        setFont(font);
        setVisible(false);
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public void setCorY(int corY) {
        this.corY = corY;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (corY > iniCorY && corY <= (iniCorY + 816) && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            velY += 1;
            repaint();
        } else if (corY >= iniCorY && corY < (iniCorY + 816) && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}
