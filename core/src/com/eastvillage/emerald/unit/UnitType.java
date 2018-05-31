package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface UnitType {

    int getMaxHealth(Unit unit);
    int getDefence(Unit unit);
    int getAttack(Unit unit);
    int getRange(Unit unit);
    int getMovementSpeed(Unit unit);
    TextureRegion getTexture(Unit unit);
}
