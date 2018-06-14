package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.spells.types.SteadyShot;
import com.eastvillage.emerald.unit.Unit;

public class Knight implements UnitType {

    @Override
    public int getBaseMaxHealth() {
        return 80;
    }

    @Override
    public int getBaseDefense() {
        return 6;
    }

    @Override
    public int getBaseMagicDefense() {
        return 3;
    }

    @Override
    public int getBaseAttack() {
        return 12;
    }

    @Override
    public int getBaseStrength() {
        return 10;
    }

    @Override
    public int getBaseAgility() {
        return 5;
    }

    @Override
    public int getBaseMagicPower() {
        return 0;
    }

    @Override
    public int getBaseRange() {
        return 1;
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
        return "Knight";
    }

    @Override
    public TextureRegion getTexture() {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.KNIGHT));
    }
}
