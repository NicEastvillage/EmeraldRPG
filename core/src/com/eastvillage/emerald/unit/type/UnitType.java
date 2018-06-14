package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.unit.Unit;

import java.util.List;

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
    AbilityType[] getAbilities();
    String getTypeName();
    TextureRegion getTexture();
}
