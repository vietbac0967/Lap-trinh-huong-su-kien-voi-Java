package custom;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author vanphatdev
 */
public class MyScrollBar extends JScrollBar {
    public MyScrollBar() {
        setUI(new ScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setBackground(new Color(222, 222, 222));
        setUnitIncrement(20);
    }
}
