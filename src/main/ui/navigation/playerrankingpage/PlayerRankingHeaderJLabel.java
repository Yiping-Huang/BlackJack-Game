package ui.navigation.playerrankingpage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerRankingHeaderJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private final Timer timer = new Timer(10,this);


    public PlayerRankingHeaderJLabel() {
        super("<html><body>&nbsp;&nbsp;&nbsp;&nbsp;Player Ranking</body></html>");
        setFont(new Font("Times New Roman", Font.BOLD, 50));
        setBounds(520,40,500,150);
        setLocation(530, 40);
        setVisible(false);
        setForeground(Color.BLACK);
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
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

