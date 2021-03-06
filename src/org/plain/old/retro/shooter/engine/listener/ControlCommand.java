package org.plain.old.retro.shooter.engine.listener;

import org.plain.old.retro.shooter.engine.unit.GeneralPlayer;
import org.plain.old.retro.shooter.engine.unit.Player;

import java.util.Objects;

/**
 * @project plain-old-retro-shooter
 * @created 29.11.2020 17:58
 * <p>
 * @author Alexander A. Kropotin
 */
public abstract class ControlCommand {

    protected GeneralPlayer controllable;

    public ControlCommand(GeneralPlayer controllable) {
        Objects.requireNonNull(controllable);
        this.controllable = controllable;
    }

    public abstract void execute();
}
