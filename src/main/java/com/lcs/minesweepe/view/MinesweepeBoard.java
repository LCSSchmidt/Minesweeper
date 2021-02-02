package com.lcs.minesweepe.view;

import com.lcs.minesweepe.model.Board;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.stream.Stream;
import javax.swing.JPanel;

public class MinesweepeBoard extends JPanel {
    
    public MinesweepeBoard(Board board) {

        setVisible(true);
        setLayout(new GridLayout(board.getLines(), board.getColumns()));
        board.forEachField(f -> add(new MsFieldButton(f)));
    }

}
