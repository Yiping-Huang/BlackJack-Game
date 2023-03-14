package ui.customstyle;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomJButtonUI extends BasicButtonUI {

    private static final int ARC_SIZE = 15;
    private final Color fillColor;

    public CustomJButtonUI(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = b.getSize();


        if (b.getModel().isArmed()) {
            g2.setColor(fillColor.darker());
        } else {
            g2.setColor(fillColor);
        }

        g2.fillRoundRect(0, 0, d.width, d.height, ARC_SIZE, ARC_SIZE);
        super.paint(g, c);
    }
}
