public class BattleshipTurnTimer extends Thread {

    private int timeLeft;
    private boolean running = true;
    private TimerListener listener;

    public interface TimerListener {
        void onTick(int timeLeft);
        void onTimeout();
    }

    public BattleshipTurnTimer(int seconds, TimerListener listener) {
        this.timeLeft = seconds;
        this.listener = listener;
    }

    public void run() {
        try {
            while (timeLeft >= 0 && running) {

                int current = timeLeft;

                javax.swing.SwingUtilities.invokeLater(() -> {
                    listener.onTick(current);
                });

                Thread.sleep(1000);
                timeLeft--;
            }

            if (running) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    listener.onTimeout();
                });
            }

        } catch (InterruptedException e) {
            System.out.println("Timer stopped");
        }
    }

    public void stopTimer() {
        running = false;
    }
}