package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Unit {

    private int owner;
    private UnitType type;

    private int currentHealth;

    public Unit(int owner, UnitType type) {
        this.type = type;
        currentHealth = type.getMaxHealth(this);
    }

    public void takeDamage(int dmg) {
        if (dmg < 0) return;
        currentHealth -= dmg;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public int getOwner() {
        return owner;
    }

    public UnitType getType() {
        return type;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return type.getMaxHealth(this);
    }

    public int getDefence() {
        return type.getDefence(this);
    }

    public int getAttack() {
        return type.getAttack(this);
    }

    public int getRange() {
        return type.getRange(this);
    }

    public int getMovementSpeed() {
        return type.getMovementSpeed(this);
    }

    public TextureRegion getTexture() {
        return type.getTexture(this);
    }
}
