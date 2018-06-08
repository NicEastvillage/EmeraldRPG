package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.unit.Unit;

public interface UnitType {

    int getBaseMaxHealth();
    int getBaseDefense();
    int getBaseMagicDefense();
    int getBaseAttack();
    int getBaseStrength();
    int getBaseAgility();
    int getBaseMagicPower();
    int getBaseRange();
    int getBaseMovementSpeed();
    String getTypeName();
    TextureRegion getTexture();
}
