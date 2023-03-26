package ui.gamephases.information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageAreaBoardJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private static int counter = 0;
    private final Image board = new ImageIcon("./images/MessageBox.png").getImage();
    private final Timer timer = new Timer(10, this);


    public MessageAreaBoardJLabel() {
        setVisible(false);
        setBounds(145,355,250,220);
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, 0, 0, 250, 220, this);
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
