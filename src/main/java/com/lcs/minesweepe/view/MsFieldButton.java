package com.lcs.minesweepe.view;

import com.lcs.minesweepe.interfaces.FieldObserver;
import com.lcs.minesweepe.model.Field;
import com.lcs.minesweepe.util.FieldAction;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MsFieldButton extends JButton implements FieldObserver, MouseListener {

    private final Color BG_STD = new Color(184, 184, 184);
    private final Color BG_MARKED = new Color(8, 179, 247);
    private final Color BG_EXPLODED = new Color(189, 66, 68);
    private final Color TEXT_GREEN = new Color(0, 100, 0);

    private final Field field;

    public MsFieldButton(Field field) {
        this.field = field;
        setOpaque(true);
        setBackground(BG_STD);
        setBorder(BorderFactory.createBevelBorder(0));
        field.addFieldObserver(this);
        addMouseListener(this);

    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == 1) {
            this.field.open();
        } else if (me.getButton() == 3) {
            this.field.alternateMarked();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void eventOccurred(Field field, FieldAction action) {
        switch (action) {
            case OPEN:
                this.applyOpenStyle();
                break;
            case MARK:
                this.applyMarkStyle();
                break;
            case MARK_OFF:
                this.applySTDStyle();
                break;
            case EXPLODE:
                this.applyExplodeStyle();
                break;
            case RESTART:
                this.applySTDStyle();
                break;
        }
    }

    private void applyOpenStyle() {
        
        if(field.isMined()) {
            applyExplodeStyle(); 
            return;
        }

        setBackground(BG_STD);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        switch (field.neighborMineCount()) {
            case 1:
                setForeground(TEXT_GREEN);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.CYAN);
        }
        
        String text = !field.safetyNeighbor() ? field.neighborMineCount() + "" : "";
        setText(text);
    }
    
    private void applyMarkStyle() {
        setBackground(BG_MARKED);
        setText("M");
    }

    private void applySTDStyle() {
        setBackground(BG_STD);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }
    
    private void applyExplodeStyle() {
        setBackground(BG_EXPLODED);
        setText("X");
    }
}
