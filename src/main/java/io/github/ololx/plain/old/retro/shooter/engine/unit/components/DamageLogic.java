package io.github.ololx.plain.old.retro.shooter.engine.unit.components;

/**
 * project plain-old-retro-shooter
 * created 2021-12-19 15:00
 *
 * @author Alexander A. Kropotin
 */
public class DamageLogic implements GameObjectLogic<Health> {

    private int value;

    public DamageLogic(int value) {
        this.value = value;
    }

    @Override
    public void apply(Health component) {
        component.decrease(value);
    }
}
