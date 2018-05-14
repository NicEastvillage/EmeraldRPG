package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight implements Unit {

    private static final TextureRegion tex = new TextureRegion(new Texture("Knight.png"));

    private int owner = -1;
    private int currentHealth = getMaxHealth();

    public Knight() {
    }

    public Knight(int owner) {
        this.owner = owner;
    }

    @Override
    public int getOwner() {
        return owner;
    }

    @Override
    public int getMaxHealth() {
        return 30;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public boolean takeDamage(int dmg) {
        if (dmg < 0) return false;
        currentHealth -= dmg;
        return currentHealth < 0;
    }

    @Override
    public int getDefence() {
        return 3;
    }

    @Override
    public int getAttack() {
        return 3;
    }

    @Override
    public int getMovementSpeed() {
        return 3;
    }

    @Override
    public TextureRegion getTexture() {
        return tex;
    }
}
