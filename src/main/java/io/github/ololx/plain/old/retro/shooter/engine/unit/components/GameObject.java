package io.github.ololx.plain.old.retro.shooter.engine.unit.components;

/**
 * project plain-old-retro-shooter
 * created 2021-12-19 07:53
 *
 * @author Alexander A. Kropotin
 */
public interface GameObject<L extends GameObjectLogic> {

     void update(L logic);
}
