package com.lcs.minesweepe.model;

import com.lcs.minesweepe.util.FieldAction;
import com.lcs.minesweepe.interfaces.FieldObserver;
import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private boolean mined;
    private boolean marked;
    private boolean open;

    private List<Field> neighBors = new ArrayList<>();
    private List<FieldObserver> fieldObserver = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;

        this.initialFieldStatus();
    }

    public void addFieldObserver(FieldObserver fieldObserver) {
        this.fieldObserver.add(fieldObserver);
    }

    public void notifyFieldObservers(FieldAction action) {
        this.fieldObserver.stream().forEach(o -> o.eventOccurred(this, action));
    }

    public int neighborMineCount() {
        return (int) this.neighBors.stream()
                .filter(n -> n.mined)
                .count();
    }

    public boolean addNeighbor(Field neighbor) {
        boolean diffLine = this.line != neighbor.line;
        boolean diffColumn = this.column != neighbor.column;
        boolean diagonal = diffLine && diffColumn;

        int deltaLine = Math.abs(this.line - neighbor.line);
        int deltaColumn = Math.abs(this.column - neighbor.column);
        int delta = Math.abs(deltaLine + deltaColumn);

        if (!diagonal && delta == 1) {
            this.neighBors.add(neighbor);
            return true;
        } else if (diagonal && delta == 2) {
            this.neighBors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    public void alternateMarked() {
        if (!this.open) {
            if (this.marked) {
                this.marked = false;
                this.notifyFieldObservers(FieldAction.MARK_OFF);
            } else {
                this.marked = true;
                this.notifyFieldObservers(FieldAction.MARK);
            }
        }
    }

    public boolean safetyNeighbor() {
        return this.neighBors.stream().noneMatch(c -> c.mined);
    }

    public boolean goalAchieved() {
        boolean opened = this.open && !this.mined;
        boolean marked = this.marked && !this.open;

        return opened || marked;
    }

    public boolean open() {
        if (!this.marked && !this.open) {
            if (this.mined) {
                this.notifyFieldObservers(FieldAction.EXPLODE);
            }

            this.setOpen();
            if (this.safetyNeighbor() && !this.mined) {
                this.neighBors.stream().forEach(n -> n.open());
            }
            return true;
        }
        return false;
    }

    public void initialFieldStatus() {
        this.mined = false;
        this.marked = false;
        this.open = false;
        
        notifyFieldObservers(FieldAction.RESTART);
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined() {
        this.mined = true;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen() {
        this.open = true;
        notifyFieldObservers(FieldAction.OPEN);
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

}
