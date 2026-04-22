import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

public class SoundManager {

    private static Clip musicClip;

    public static boolean muteMusic = false;
    public static boolean muteSFX = false;

    private static Map<String, Clip> sfxCache = new HashMap<>();

    // ================= MUSIC =================

    public static void playMusic(String path) {

        if (muteMusic)
            return;

        try {

            if (musicClip != null && musicClip.isRunning())
                return;

            java.net.URL url = SoundManager.class.getResource(path);

            if (url == null) {
                System.out.println("Missing music: " + path);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            musicClip = AudioSystem.getClip();
            musicClip.open(audio);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();

        } catch (Exception e) {
            System.out.println("Music error");
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }

    public static void updateMusicState(String path) {
        if (muteMusic) {
            stopMusic();
        } else {
            playMusic(path);
        }
    }

    // ================= SFX =================

    public static void playSFX(String path) {

        if (muteSFX)
            return;

        try {

            Clip clip;

            // 🔥 reuse cached clip
            if (sfxCache.containsKey(path)) {
                clip = sfxCache.get(path);
                clip.setFramePosition(0);
            } else {

                java.net.URL url = SoundManager.class.getResource(path);

                if (url == null) {
                    System.out.println("Missing SFX: " + path);
                    return;
                }

                AudioInputStream audio = AudioSystem.getAudioInputStream(url);

                clip = AudioSystem.getClip();
                clip.open(audio);

                sfxCache.put(path, clip);
            }

            clip.start();

        } catch (Exception e) {
            System.out.println("SFX error");
            e.printStackTrace();
        }
    }
}