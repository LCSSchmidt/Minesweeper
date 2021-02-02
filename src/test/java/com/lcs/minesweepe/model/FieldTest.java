package com.lcs.minesweepe.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FieldTest {

    private Field field;

    public FieldTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
    @Before
    public void setUpField() {
        field = new Field(2, 2);
    }

//    @AfterEach
//    public void tearDown() {
//    }
    @Test
    public void testFieldNeighborLeft() {
        Field neighbor = new Field(2, 1);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborRight() {
        Field neighbor = new Field(2, 3);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborTop() {
        Field neighbor = new Field(1, 2);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborBottom() {
        Field neighbor = new Field(3, 2);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborDiagonalLeftTop() {
        Field neighbor = new Field(1, 1);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborDiagonalRightTop() {
        Field neighbor = new Field(1, 3);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborDiagonalLeftBottom() {
        Field neighbor = new Field(3, 1);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNeighborDiagonalRightBottom() {
        Field neighbor = new Field(3, 3);
        boolean result = this.field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    public void testFieldNotNeighborOnDiagonal() {
        Field neighbor = new Field(0, 0);
        boolean result;

        result = this.field.addNeighbor(neighbor);
        assertFalse(result);
    }

    @Test
    public void testFieldNotNeighborOnSide() {
        Field neighbor = new Field(2, 0);
        boolean result;

        result = this.field.addNeighbor(neighbor);
        assertFalse(result);
    }

    // ################################ Testing Open Alternatives
    @Test
    public void testOpenField() {
        assertTrue(field.open());
    }

    @Test
    public void testOpenMinedField() {
        field.setMined();
        assertTrue(field.open());
    }

    @Test
    public void testOpenMarkedField() {
        field.alternateMarked();
        assertFalse(field.open());
    }

    @Test
    public void testMarkeField() {
        field.alternateMarked();
        assertTrue(field.isMarked());
    }

    @Test
    public void testDoubleMarkeField() {
        field.alternateMarked();
        field.alternateMarked();
        assertFalse(field.isMarked());
    }

    @Test
    public void testNotOpenNeighbor() {
        Field neighbor21 = new Field(2, 1);
        Field neighbor32 = new Field(3, 2);
        Field neighbor13 = new Field(1, 3);

        field.addNeighbor(neighbor21);
        field.addNeighbor(neighbor32);
        field.addNeighbor(neighbor13);

        neighbor13.setMined();
        field.open();

        assertTrue(!neighbor21.isOpen() && !neighbor13.isOpen());
    }
    
    @Test
    public void testOpenNeighbor() {
        Field neighbor21 = new Field(2, 1);
        Field neighbor32 = new Field(3, 2);
        Field neighbor13 = new Field(1, 3);

        field.addNeighbor(neighbor21);
        field.addNeighbor(neighbor32);
        field.addNeighbor(neighbor13);

        field.open();

        assertTrue(neighbor21.isOpen() && neighbor13.isOpen());
    }
}
