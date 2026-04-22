import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {

    private static final String FILE_NAME = "scores.txt";

    public static int getBestScore(String gameId) {

        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return 0;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2 && parts[0].equals(gameId)) {
                    br.close();
                    return Integer.parseInt(parts[1]);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void updateBestScore(String gameId, int newScore) {

        Map<String, Integer> scores = new HashMap<>();

        try {
            File file = new File(FILE_NAME);

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("=");

                    if (parts.length == 2) {
                        scores.put(parts[0], Integer.parseInt(parts[1]));
                    }
                }

                br.close();
            }
        } catch (Exception e) {}

        int oldScore = scores.getOrDefault(gameId, 0);

        if (newScore > oldScore) {
            scores.put(gameId, newScore);
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (String key : scores.keySet()) {
                bw.write(key + "=" + scores.get(key));
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}