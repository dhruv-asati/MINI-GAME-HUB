import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ImageLoader {

    public static List<CardData> getCards(int totalCards, int size) {

        List<CardData> cards = new ArrayList<>();

        try {
            File folder = new File("images/fruits");

            if (!folder.exists() || !folder.isDirectory()) {
                throw new Exception("fruits folder missing!");
            }

            File[] files = folder.listFiles();

            if (files == null || files.length == 0) {
                throw new Exception("No images found!");
            }

            Arrays.sort(files);
            List<File> list = new ArrayList<>(Arrays.asList(files));
            Collections.shuffle(list);

            int pairs = totalCards / 2;

            if (list.size() < pairs) {
                throw new Exception("Not enough images!");
            }

            List<File> selected = list.subList(0, pairs);

            int id = 0;

            for (File f : selected) {

                ImageIcon icon = loadImage(f.getPath(), size);

                cards.add(new CardData(id, icon));
                cards.add(new CardData(id, icon));

                id++;
            }

            Collections.shuffle(cards);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

        return cards;
    }

    private static ImageIcon loadImage(String path, int maxSize) {

        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();

        int w = img.getWidth(null);
        int h = img.getHeight(null);

        double scale = Math.min((double) maxSize / w, (double) maxSize / h);

        int newW = (int) (w * scale);
        int newH = (int) (h * scale);

        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}