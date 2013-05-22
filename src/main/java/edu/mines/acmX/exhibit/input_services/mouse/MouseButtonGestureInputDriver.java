package edu.mines.acmX.exhibit.input_services.mouse;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.mines.acmX.exhibit.input_services.ButtonGestureInputDriver;

/**
 * Input driver which translates mouse button events into gestures.
 *
 * Note that it does not handle the scroll wheel, since Java curiously uses an
 * entirely separate interface for that.
 */
public final class MouseButtonGestureInputDriver
    extends ButtonGestureInputDriver
    implements MouseListener {
    public void installInto(Component man) {
        man.addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        pressed(e.getButton());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

	public float[][] getPointers() {
		return null;
	}
}
