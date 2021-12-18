package org.plain.old.retro.shooter.engine.listener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander A. Kropotin
 * @created 29.11.2020 18:03
 * <p>
 * @author Alexander A. Kropotin
 */
public class MouseController implements MouseMotionListener {

    /**
     * The Center.
     */
    private Point center;

    /**
     * The Commands.
     */
    private final Map<Integer, ControlCommand> commands;

    /**
     * The State.
     */
    private List<ControlCommand> state;

    /**
     * The Robot.
     */
    private Robot robot;

    /**
     * The Esc.
     */
    boolean esc = false;

    /**
     * Instantiates a new Input controller.
     */
    public MouseController() {
        this(null);
    }

    /**
     * Instantiates a new Input controller.
     *
     * @param commands the keys
     */
    public MouseController(Map<Integer, ControlCommand> commands) {
        this(commands, new Point(0, 0));
    }

    /**
     * Instantiates a new Input controller.
     *
     * @param commands   the keys
     * @param center the center
     */
    public MouseController(Map<Integer, ControlCommand> commands, Point center) {
        this.commands = commands == null ? Collections.EMPTY_MAP : commands;
        state = new ArrayList<>();

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        this.center = center;
        robot.mouseMove(center.x, center.y);
    }

    public void execute() {
        List<ControlCommand> commandsTmp = new ArrayList<>(this.state);
        this.state.clear();
        commandsTmp.forEach(command -> {
            command.execute();
        });
    }

    public void enable() {
        this.esc = true;
    }

    public void disable() {
        this.esc = false;
    }

    public void swtch() {
        this.esc ^= true;
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
        if (!esc) this.mouseMoverHorizont(e);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (esc) return;

        this.mouseMoverHorizont(e);
    }

    /**
     * Mouse move.
     */
    private void mouseMove() {
        robot.mouseMove(this.center.x, this.center.y);
    }

    /**
     * Mouse mover horizont.
     *
     * @param e the e
     */
    private void mouseMoverHorizont(MouseEvent e) {
        if (e.getXOnScreen() > this.center.x) this.state.add(this.commands.get(MouseEvent.MOUSE_MOVED + 2));
        else if (e.getXOnScreen() < this.center.x) this.state.add(this.commands.get(MouseEvent.MOUSE_MOVED + 1));

        this.mouseMove();
    }
}