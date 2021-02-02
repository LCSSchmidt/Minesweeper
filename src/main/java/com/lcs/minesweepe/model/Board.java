package com.lcs.minesweepe.model;

import com.lcs.minesweepe.util.FieldAction;
import com.lcs.minesweepe.interfaces.FieldObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Board implements FieldObserver {
    private final int columns;
    private final int lines;
    private final int mineQtd;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<Boolean>> resultGameObservers = new ArrayList<>();

    public Board(int columns, int lines, int mineQtd) {
        this.columns = columns;
        this.lines = lines;
        this.mineQtd = mineQtd;

        genFields();
        mineFields();
        associateNeighbors();
    }

    private void genFields() {
        
        for (int line = 0; line < this.lines; line++) {
            for (int column = 0; column < this.columns; column++) {
                Field field = new Field(line, column);
                field.addFieldObserver(this);
                this.fields.add(field);
            }
        }
    }

    private void associateNeighbors() {
        for (Field field : fields) {
            for (Field possibleNeighbor : fields) {
                field.addNeighbor(possibleNeighbor);
            }
        }
    }

    private void mineFields() {
        int minedFields = 0;
        while (minedFields < this.mineQtd) {
            int line = (int)(Math.random() * (this.fields.size()));
            this.fields.get(line).setMined();
            minedFields = (int)fields.stream().filter(f -> f.isMined()).count();
        }
    }
    
    public void forEachField(Consumer<Field> consumer)  {
        this.fields.stream().forEach(consumer);
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }
    
    private void showMines() {
        this.fields.stream()
                .filter(f -> f.isMined())
                .filter(f -> !f.isMarked())
                .forEach(f -> f.setOpen());
    }

    @Override
    public void eventOccurred(Field field, FieldAction action) {
        if(action == FieldAction.EXPLODE) {
            this.notifyObservers(false);
            this.showMines();
        }else if(this.winGame()) {
            this.notifyObservers(true);
        }
    }
    
    public void addObserver(Consumer<Boolean> resultGameOb) {
        this.resultGameObservers.add(resultGameOb);
    }
    
    public void notifyObservers(boolean result) {
        this.resultGameObservers.stream()
                .forEach(o -> o.accept(result));
    }

    public boolean winGame() {
        System.out.println("Win Game: " + fields.stream().allMatch(f -> f.goalAchieved()));
        return fields.stream().allMatch(f -> f.goalAchieved());
    }
    
    public boolean losedGame() {
        return fields.stream()
                .anyMatch(f -> f.isMined());
                
    }
    
    public boolean openField(int line, int column) {
        fields.stream().filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.open());
        
        return this.winGame();
    }
    
    public boolean markField(int line, int column) {
        fields.stream().filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.alternateMarked());
    
        return this.winGame();
    }
    
    public void restartGame() {
        this.fields.stream().forEach(f -> f.initialFieldStatus());
        
        this.mineFields();
    }
}
