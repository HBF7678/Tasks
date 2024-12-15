import java.awt.*;

public class Brick {
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private Color color;
    private int breakAnimationCounter; // Counter for animation

    public Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true;
        this.color = Color.BLUE; // Default color
        this.breakAnimationCounter = 0;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (!visible) {
            breakAnimationCounter = 10; // Start animation
        }
    }

    public void update() {
        if (breakAnimationCounter > 0) {
            breakAnimationCounter--;
            if (breakAnimationCounter == 0) {
                // Animation complete, set to invisible
                isVisible = false;
            }
        }
    }

    public void draw(Graphics g) {
        if (isVisible) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        } else if (breakAnimationCounter > 0) {
            // Change color during animation
            g.setColor(new Color(255, 0, 0, (int)(255 * (breakAnimationCounter / 10.0)))); // Red with fading effect
            g.fillRect(x, y, width, height);
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Color getColor() {
        return color;
    }
}
