package com.lcs.minesweepe.view;

import com.lcs.minesweepe.model.Board;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainPainel extends JFrame {

    public MainPainel() throws HeadlessException {
        Board board = new Board(30, 16, 99);
//        Board board = new Board(3, 3, 2);
        add(new MinesweepeBoard(board));

        board.addObserver((gameState) -> {

            SwingUtilities.invokeLater(() -> {
                if (gameState) {
                    JOptionPane.showMessageDialog(this, "Winn");
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over");
                }
                board.restartGame();
            });
        });
        setTitle("Minesweepe");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPainel();
    }
}
