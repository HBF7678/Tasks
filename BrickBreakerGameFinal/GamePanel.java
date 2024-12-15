import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks; // Change to List for dynamic sizing
    @SuppressWarnings("unused")
    private int score;
    @SuppressWarnings("unused")
    private int level;
    @SuppressWarnings("unused")
    private int lives;
    private boolean gameOver; // Flag to track game over state
    private JButton retryButton; // Button to retry the game
    private Random random;

    public GamePanel() {
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1280, 720)); // Set resolution to 720p
        setLayout(null); // Set layout to null to manually position components

        timer = new Timer(10, this);
        timer.start();

        paddle = new Paddle(350, 550, 100, 20); // Reset paddle position
        ball = new Ball(400, 300, 20); // Reset ball position
        bricks = new ArrayList<>(); // Initialize the list
        random = new Random();
        initializeBricks(); // Method to initialize bricks

        score = 0;
        level = 1;
        lives = 3;
        gameOver = false; // Initialize game over state

        retryButton = new JButton("Retry");
        retryButton.addActionListener(_ -> resetGame());
        retryButton.setVisible(false); // Initially hidden
        retryButton.setBounds(550, 350, 100, 30); // Set bounds for retry button
        add(retryButton);
    }

    private void initializeBricks() {
        for (int i = 0; i < 30; i++) { // Initial number of bricks
            addRandomBrick();
        }
    }

    private void addRandomBrick() {
        int width = 75;
        int height = 20;
        int x = random.nextInt(800 - width); // Random x position within bounds
        int y = random.nextInt(300); // Random y position within bounds

        // Ensure the new brick does not overlap with existing bricks
        for (Brick brick : bricks) {
            if (brick.isVisible() &&
                new Rectangle(x, y, width, height).intersects(brick.getBounds())) {
                return; // If overlaps, do not add this brick
            }
        }

        bricks.add(new Brick(x, y, width, height)); // Add new brick
    }

    private void checkWinCondition() {
        boolean allBlueBricksBroken = true;
        for (Brick brick : bricks) {
            if (brick.isVisible() && brick.getColor() == Color.BLUE) {
                allBlueBricksBroken = false;
                break;
            }
        }
        if (allBlueBricksBroken) {
            gameOver = true;
            // Display win message (you can customize this)
            JOptionPane.showMessageDialog(this, "You Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ball.move(this); // Pass the current instance of GamePanel to the ball's move method
            for (Brick brick : bricks) {
                brick.update(); // Update each brick for animation
                if (brick.isVisible() && ball.checkCollision(brick)) {
                    brick.setVisible(false);
                    ball.reflectY();
                    addRandomBrick(); // Add a new brick when one is broken
                    checkWinCondition(); // Check if all bricks are broken
                    score += 10;
                }
            }

            // Paddle collision detection
            if (ball.getY() + ball.getDiameter() >= paddle.getY() &&
                ball.getX() + ball.getDiameter() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth()) {
                ball.reflectY();
            }

            // Check for game over
            if (ball.getY() + ball.getDiameter() > paddle.getY() + paddle.getHeight()) {
                setGameOver(); // Set game over flag
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);
        for (Brick brick : bricks) {
            brick.draw(g);
        }

        // Draw game over overlay if game is over
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
            retryButton.setVisible(true); // Show the retry button
        }
    }

    private void resetGame() {
        // Reset game state
        score = 0;
        gameOver = false;
        paddle = new Paddle(350, 550, 100, 20); // Reset paddle position
        ball = new Ball(400, 300, 20); // Reset ball position
        bricks.clear();
        initializeBricks();
        retryButton.setVisible(false); // Hide the retry button
    }

    public void setGameOver() {
        gameOver = true;
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            if (paddle.getX() > 0) {
                paddle.setX(paddle.getX() - 30); // Increase paddle speed to 30
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (paddle.getX() < getWidth() - paddle.getWidth()) {
                paddle.setX(paddle.getX() + 30); // Increase paddle speed to 30
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public List<Brick> getBricks() {
        return bricks;
    }
}
