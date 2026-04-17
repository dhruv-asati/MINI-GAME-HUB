import java.awt.*;

public class Enemy {

    public int x, y;
    public int width = 50;
    public int height = 80;
    public int speed = 6;

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

        // body
        g2.setColor(new Color(200, 30, 30));
        g2.fillRoundRect(x, y, width, height, 15, 15);

        // windshield
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x + 10, y + 10, 30, 20, 10, 10);

        // wheels
        g2.setColor(Color.BLACK);
        g2.fillOval(x + 5, y + 60, 10, 15);
        g2.fillOval(x + 35, y + 60, 10, 15);

        // headlights
        g2.setColor(Color.YELLOW);
        g2.fillOval(x + 5, y + 5, 8, 8);
        g2.fillOval(x + 37, y + 5, 8, 8);
    }
}