package ui.background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class represent the Main Menu at the start of the Black Jack Game. This is a pop-up menu with different
// navigation buttons

public class NavigationBoardJLabel extends JLabel implements ActionListener {

    private final Timer timer = new Timer(10,this);
    private static int corY = 0;
    private static String status = "Showing";
    private final Image board = new ImageIcon("./images/BlankBoard.png").getImage();

    // EFFECTS: construct the Main Menu Panel and printout the content
    public NavigationBoardJLabel() {
        setVisible(true);
        setBounds(435,60,600,1500);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, 0, corY, 600, 700, this);
        timer.start();
    }

    public void setCorY(int n) {
        corY = n;
    }

    public void setStatus(String s) {
        status = s;
    }

    public void stopTimer() {
        timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int velY = 27;
        if (corY > 0 && corY <= 1000 && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (corY >= 0 && corY < 1000 && status.equals("Moving Down")) {
            setVisible(true);
            corY += velY;
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        }
    }
}
