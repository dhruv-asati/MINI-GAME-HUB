import java.util.List;
import javax.swing.*;

public class GameLogic {

    private JButton first = null;
    private JButton second = null;

    private int firstID = -1;
    private int secondID = -1;

    private boolean lock = false;

    private List<CardData> cards;
    private ImageIcon cover;

    private int matchedPairs = 0;
    private int totalPairs;

    private Runnable onWinCallback;

    public GameLogic(List<CardData> cards, ImageIcon cover, Runnable onWin) {
        this.cards = cards;
        this.cover = cover;
        this.totalPairs = cards.size() / 2;
        this.onWinCallback = onWin;
    }

    public void handleClick(JButton card, int index) {

        if (lock) return;

        Boolean revealed = (Boolean) card.getClientProperty("revealed");
        Boolean matched = (Boolean) card.getClientProperty("matched");

        if (revealed != null && revealed) return;
        if (matched != null && matched) return;

        card.setIcon(cards.get(index).icon);
        card.putClientProperty("revealed", true);

        if (first == null) {
            first = card;
            firstID = cards.get(index).id;
        } else {
            second = card;
            secondID = cards.get(index).id;

            lock = true;

            Timer timer = new Timer(600, e -> checkMatch());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void checkMatch() {

        if (firstID == secondID) {

            first.putClientProperty("matched", true);
            second.putClientProperty("matched", true);

            matchedPairs++;

            // 🔥 WIN CONDITION
            if (matchedPairs == totalPairs) {
                onWinCallback.run();
            }

        } else {

            first.setIcon(cover);
            second.setIcon(cover);

            first.putClientProperty("revealed", false);
            second.putClientProperty("revealed", false);
        }

        first = null;
        second = null;
        firstID = -1;
        secondID = -1;
        lock = false;
    }
}