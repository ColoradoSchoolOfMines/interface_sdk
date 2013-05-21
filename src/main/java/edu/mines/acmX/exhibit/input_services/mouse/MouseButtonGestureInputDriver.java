package edu.mines.csci598.recycler.backend;

import java.awt.event.*;

/**
 * Input driver which translates mouse button events into gestures.
 *
 * Note that it does not handle the scroll wheel, since Java curiously uses an
 * entirely separate interface for that.
 */
public final class MouseButtonGestureInputDriver
    extends ButtonGestureInputDriver
    implements MouseListener {
    public void installInto(GameManager man) {
        man.getCanvas().addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        pressed(e.getButton());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
