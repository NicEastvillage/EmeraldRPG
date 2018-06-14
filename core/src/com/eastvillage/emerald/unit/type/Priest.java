package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.spells.types.SteadyShot;
import com.eastvillage.emerald.unit.Unit;

public class Priest implements UnitType {

    @Override
    public int getBaseMaxHealth() {
        return 70;
    }

    @Override
    public int getBaseDefense() {
        return 3;
    }

    @Override
    public int getBaseMagicDefense() {
        return 4;
    }

    @Override
    public int getBaseAttack() {
        return 14;
    }

    @Override
    public int getBaseStrength() {
        return 4;
    }

    @Override
    public int getBaseAgility() {
        return 3;
    }

    @Override
    public int getBaseMagicPower() {
        return 6;
    }

    @Override
    public int getBaseRange() {
        return 2;
    }

    @Override
    public int getBaseMovementSpeed() {
        return 3;
    }

    @Override
    public AbilityType[] getAbilities() {
        return new AbilityType[] {};
    }

    @Override
    public String getTypeName() {
        return "Priest";
    }

    @Override
    public TextureRegion getTexture() {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.PRIEST));
    }
}
