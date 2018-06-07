package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.unit.Unit;

public interface UnitType {

    int getMaxHealth(Unit unit);
    int getDefence(Unit unit);
    int getAttack(Unit unit);
    int getRange(Unit unit);
    int getMovementSpeed(Unit unit);
    String getTypeName();
    TextureRegion getTexture(Unit unit);
}
