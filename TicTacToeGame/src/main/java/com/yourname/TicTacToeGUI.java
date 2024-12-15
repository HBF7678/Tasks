package com.yourname;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame {
    private CircularButton[][] buttons;
    private char currentPlayer;
    private JPanel panel;

    public TicTacToeGUI() {
        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentPlayer = 'X';
        buttons = new CircularButton[3][3];
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setBackground(new Color(240, 240, 240)); // Light background color

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new CircularButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(new Color(255, 255, 255)); // White background for buttons
                buttons[i][j].setForeground(new Color(0, 0, 0)); // Black text
                buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panel.add(buttons[i][j]);
            }
        }

        add(panel);
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setBackground(currentPlayer == 'X' ? new Color(173, 216, 230) : new Color(255, 182, 193)); // Light blue for X and light pink for O
                if (checkWin(currentPlayer)) {
                    JOptionPane.showMessageDialog(null, currentPlayer + " wins!");
                    resetGame();
                } else if (checkTie()) {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    private boolean checkWin(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(player)) && 
                 buttons[i][1].getText().equals(String.valueOf(player)) && 
                 buttons[i][2].getText().equals(String.valueOf(player))) ||
                (buttons[0][i].getText().equals(String.valueOf(player)) && 
                 buttons[1][i].getText().equals(String.valueOf(player)) && 
                 buttons[2][i].getText().equals(String.valueOf(player)))) {
                return true;
            }
        }
        if ((buttons[0][0].getText().equals(String.valueOf(player)) && 
             buttons[1][1].getText().equals(String.valueOf(player)) && 
             buttons[2][2].getText().equals(String.valueOf(player))) ||
            (buttons[0][2].getText().equals(String.valueOf(player)) && 
             buttons[1][1].getText().equals(String.valueOf(player)) && 
             buttons[2][0].getText().equals(String.valueOf(player)))) {
            return true;
        }
        return false;
    }

    private boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(new Color(255, 255, 255)); // Reset button background
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
