import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ImageLoader {

    public static List<CardData> getCards(int totalCards, int size) {

        List<CardData> cards = new ArrayList<>();

        try {

            // 🔥 Load list from resource file (NO File API)
            List<String> images = loadImageList("/resources/fruits.txt");

            if (images.isEmpty()) {
                throw new Exception("No images found in fruits.txt");
            }

            Collections.shuffle(images);

            int pairs = totalCards / 2;

            if (images.size() < pairs) {
                throw new Exception("Not enough images in fruits.txt");
            }

            int id = 0;

            for (int i = 0; i < pairs; i++) {

                ImageIcon icon = loadImage(images.get(i), size);

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

    // ================= LOAD LIST FROM TXT =================
    private static List<String> loadImageList(String path) {

        List<String> list = new ArrayList<>();

        try {

            InputStream is = ImageLoader.class.getResourceAsStream(path);

            if (is == null) {
                throw new RuntimeException("Missing resource file: " + path);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(line.trim());
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= SAFE IMAGE LOADER =================
    private static ImageIcon loadImage(String path, int maxSize) {

        java.net.URL url = ImageLoader.class.getResource(path);

        if (url == null) {
            System.out.println("Missing: " + path);
            return new ImageIcon();
        }

        ImageIcon icon = new ImageIcon(url);
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