package io.github.ololx.plain.old.retro.shooter.engine.unit.components;

/**
 * project plain-old-retro-shooter
 * created 2021-12-19 14:46
 * <p>
 * @author Alexander A. Kropotin
 */
public interface GameObjectLogic<C extends GameObjectComponent> {

     void apply(C component);
}
