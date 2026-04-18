import java.awt.*;

public class Enemy {

    public int x, y;

    // 🔥 BIGGER CARS
    public int width = 70;
    public int height = 100;

    public int speed = 7;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(new Color(200, 30, 30));
        g2.fillRoundRect(x, y, width, height, 15, 15);

        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x + 12, y + 12, 45, 25, 10, 10);

        g2.fillOval(x + 8, y + 75, 15, 20);
        g2.fillOval(x + 47, y + 75, 15, 20);

        g2.setColor(Color.YELLOW);
        g2.fillOval(x + 8, y + 6, 10, 10);
        g2.fillOval(x + 52, y + 6, 10, 10);
    }
}