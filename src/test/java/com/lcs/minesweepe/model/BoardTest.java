package com.lcs.minesweepe.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    public BoardTest() {
    }

    @Before
    public void setUp() {
//        Board board = new Board(0, 0, 0)
    }

    @Test
    public void testWinGameWithoutMine() {
        Board board = new Board(3, 3, 0);
        assertTrue(board.openField(0, 0));
    }

    @Test
    public void testWinGameFullMineFullMarked() {
        Board board = new Board(3, 3, 9);

        board.markField(0, 0);
        board.markField(0, 1);
        board.markField(0, 2);
        board.markField(1, 0);
        board.markField(1, 1);
        board.markField(1, 2);
        board.markField(2, 0);
        board.markField(2, 1);
        assertTrue(board.markField(2, 2));
    }

    @Test
    public void testGameOver() {
        Board board = new Board(3, 3, 9);
        board.openField(0, 0);
        
        assertTrue(board.losedGame());
    }
}
