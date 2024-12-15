package com.yourname;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public TicTacToe() {
        board = new Board();
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');
        currentPlayer = player1;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            board.display();
            System.out.println(currentPlayer.getName() + "'s turn. Enter row and column (0-2): ");
            int row;
            int col;
            while (true) {
                try {
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                    if (row < 0 || row > 2 || col < 0 || col > 2) {
                        System.out.println("Row and column must be between 0 and 2.");
                        continue;
                    }
                    if (board.isValidMove(row, col)) {
                        break;
                    } else {
                        System.out.println("This move is not valid. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers only.");
                    scanner.next(); // clear the invalid input
                }
            }
                
            board.placeMark(row, col, currentPlayer.getSymbol());
            if (board.checkWin(currentPlayer.getSymbol())) {
                board.display();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
