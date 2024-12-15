import java.awt.*;

public class Ball {
    private int x, y;
    private int diameter;
    private int xSpeed, ySpeed;

    public Ball(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = 2;
        this.ySpeed = -2;
    }

    public void move(GamePanel gamePanel) {
        x += xSpeed;
        y += ySpeed;

        // Wall collision detection
        if (x < 0) {
            x = 0;
            xSpeed = -xSpeed;
        }
        if (x + diameter > 1280) {
            x = 1280 - diameter;
            xSpeed = -xSpeed;
        }
        if (y < 0) {
            ySpeed = -ySpeed;
        }

        // Check for game over condition (5 cm below the paddle)
        if (y + diameter > 550) { // Assuming paddle is at y = 550
            gamePanel.setGameOver(); // Set game over in GamePanel
        }

        // Check for brick collisions
        for (Brick brick : gamePanel.getBricks()) {
            if (brick.isVisible() && this.getBounds().intersects(brick.getBounds())) {
                brick.setVisible(false); // Break the brick
                ySpeed = -ySpeed; // Bounce back
                break; // Only break one brick per bounce
            }
        }

        // Bottom wall collision (game over)
        if (y + diameter > 720) {
            x = 640;
            y = 420;
            ySpeed = -2;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, diameter, diameter);
    }

    public void reflectY() {
        ySpeed = -ySpeed;
        ySpeed *= 1.30; // Increase speed by 30% on each bounce
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public boolean checkCollision(Brick brick) {
        return (x + diameter > brick.getX() && x < brick.getX() + brick.getWidth() &&
                y + diameter > brick.getY() && y < brick.getY() + brick.getHeight() && brick.isVisible());
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public int getX() {
        return x;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }
}
