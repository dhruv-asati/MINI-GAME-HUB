import javax.swing.*;
import java.awt.*;

public class WhackMain {

    public WhackMain() {

        JFrame frame = new JFrame("Whack-a-Shaheer");

        // ✅ KEEP TITLE BAR
        frame.setUndecorated(false);

        // ✅ MAXIMIZED WINDOW (with buttons visible)
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ✅ FIXED IMAGE LOADING
        Image bg = loadImage("/images/whack.png");

        frame.add(new WhackGamePanel(bg));

        frame.setVisible(true);
    }

    // ✅ SAME SAFE LOADER STYLE
    static Image loadImage(String path) {
        java.net.URL url = WhackMain.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        System.out.println("Missing: " + path);
        return null;
    }
}