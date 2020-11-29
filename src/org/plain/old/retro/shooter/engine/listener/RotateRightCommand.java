package org.plain.old.retro.shooter.engine.listener;

import org.plain.old.retro.shooter.engine.unit.GeneralPlayer;

/**
 * @project plain-old-retro-shooter
 * @created 29.11.2020 18:03
 * <p>
 * @author Alexander A. Kropotin
 */
public class RotateRightCommand extends ControlCommand {


    public RotateRightCommand(GeneralPlayer controllable) {
        super(controllable);
    }

    @Override
    public void execute() {
        this.controllable.moveRight();
    }
}
