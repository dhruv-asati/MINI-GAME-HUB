import java.awt.*;

public class Enemy {

    public int x, y;

    public int width = 100;
    public int height = 110;

    public int speed = 7;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public Rectangle getBounds() {
        // shift collision box left by 10px
        return new Rectangle(x - 10, y, width, height);
    }

    public void draw(Graphics2D g2) {

        int drawX = x - 10; // 🔥 shift everything left by 10px

        // body
        g2.setColor(new Color(200, 30, 30));
        g2.fillRoundRect(drawX, y, width, height, 18, 18);

        // windshield
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(drawX + width / 6, y + 12, width * 2 / 3, 28, 12, 12);

        // wheels
        g2.fillOval(drawX + 10, y + height - 25, 18, 25);
        g2.fillOval(drawX + width - 28, y + height - 25, 18, 25);

        // headlights
        g2.setColor(Color.YELLOW);
        g2.fillOval(drawX + 10, y + 6, 12, 12);
        g2.fillOval(drawX + width - 22, y + 6, 12, 12);
    }
}