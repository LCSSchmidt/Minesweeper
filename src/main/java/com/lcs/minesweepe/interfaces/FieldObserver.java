package com.lcs.minesweepe.interfaces;

import com.lcs.minesweepe.util.FieldAction;
import com.lcs.minesweepe.model.Field;

@FunctionalInterface
public interface FieldObserver {
    public void eventOccurred(Field field, FieldAction action);
}
