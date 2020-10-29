package org.plain.old.retro.shooter.engine.listener;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class InputController implements KeyListener, FocusListener, MouseMotionListener, MouseListener {

    private Point center;

    private final Map<Integer, String> keys;

    private Map<String, Boolean> state;

    private Robot robot;

    boolean right, left;

    public InputController() {
        this(null);
    }

    public InputController(Map<Integer, String> keys) {
        this(keys, new Point(0, 0));
    }

    public InputController(Map<Integer, String> keys, Point center) {
        this.keys = keys == null ? Collections.EMPTY_MAP : keys;
        state = keys.entrySet().stream()
                .collect(Collectors.toMap(
                        key -> key.getValue(),
                        key -> false
                ));
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        this.center = center;
        robot.mouseMove(center.x, center.y);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (this.keys.containsKey(key.getKeyCode())) this.state.put(this.keys.get(key.getKeyCode()), true);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (this.keys.containsKey(key.getKeyCode())) this.state.put(this.keys.get(key.getKeyCode()), false);
    }

    @Override
    public void keyTyped(KeyEvent key) {
        this.keyPressed(key);
        this.keyReleased(key);
    }

    public Map<String, Boolean> getState() {
        this.mouseMovePick();
        return this.state;
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getXOnScreen() > this.center.x) {
            this.right = true;
            this.left = false;
        } else if (e.getXOnScreen() < this.center.x) {
            this.right = false;
            this.left = true;
        } else {
            this.right = false;
            this.left = false;
        }

        this.state.put(this.keys.get(KeyEvent.VK_LEFT), left);
        this.state.put(this.keys.get(KeyEvent.VK_RIGHT), right);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getXOnScreen() > this.center.x) {
            this.right = true;
            this.left = false;
        } else if (e.getXOnScreen() < this.center.x) {
            this.right = false;
            this.left = true;
        } else {
            this.right = false;
            this.left = false;
        }

        this.state.put(this.keys.get(MouseEvent.MOUSE_MOVED + 1), left);
        this.state.put(this.keys.get(MouseEvent.MOUSE_MOVED + 2), right);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.state.put(this.keys.get(KeyEvent.VK_SPACE), true);
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.state.put(this.keys.get(KeyEvent.VK_SPACE), false);
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //this.mouseMovePick();
        //robot.mouseMove(this.center.x, this.center.y);
    }

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusGained(FocusEvent e) {
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusLost(FocusEvent e) {
    }

    private void mouseMovePick() {
        robot.mouseMove(this.center.x, this.center.y);
    }
}