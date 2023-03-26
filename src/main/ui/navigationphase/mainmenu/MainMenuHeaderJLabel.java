package ui.navigationphase.mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuHeaderJLabel extends JLabel implements ActionListener {

    private final Timer timer = new Timer(10,this);
    private static int corY = 0;
    private int velY = 10;
    private static String status = "Showing";
    private final Image board = new ImageIcon("./images/BlackJackTitle.png").getImage();

    // EFFECTS: construct the Main Menu Panel and printout the content
    public MainMenuHeaderJLabel() {
        setVisible(true);
        setBounds(465,70,540,1000);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, 0, corY, 540, 270, this);
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
        if (corY > 0 && corY <= 816 && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            corY = 0;
            setVisible(true);
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}
