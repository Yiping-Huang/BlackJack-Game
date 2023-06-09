package ui.background;

import javax.swing.*;
import java.awt.*;

public class BackgroundColorJPanel extends JPanel {

    public final Color colorBackground = new Color(51,153,51);

    public BackgroundColorJPanel() {
        setBounds(0, 0, 1470, 842); //设定边缘防止Label之间相互冲突
        setOpaque(true);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(colorBackground);
        g.fillRect(0, 0, 1470, 818);
    }
}
