import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {

    // 🔥 Store in user directory (SAFE for EXE)
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "MiniGameHub";

    private static final String FILE_PATH = DIR_PATH + File.separator + "scores.txt";

    // 🔥 Ensure folder + file exist
    private static void ensureFile() {
        try {
            File dir = new File(DIR_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getBestScore(String gameId) {

        ensureFile();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2 && parts[0].equals(gameId)) {
                    return Integer.parseInt(parts[1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void updateBestScore(String gameId, int newScore) {

        ensureFile();

        Map<String, Integer> scores = new HashMap<>();

        // 🔹 Read existing scores
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int oldScore = scores.getOrDefault(gameId, 0);

        if (newScore > oldScore) {
            scores.put(gameId, newScore);
        }

        // 🔹 Write back
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (String key : scores.keySet()) {
                bw.write(key + "=" + scores.get(key));
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}