package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Unit {

    int getOwner();
    int getMaxHealth();
    int getCurrentHealth();
    boolean takeDamage(int dmg);
    int getDefence();
    int getAttack();
    int getMovementSpeed();
    TextureRegion getTexture();
}
