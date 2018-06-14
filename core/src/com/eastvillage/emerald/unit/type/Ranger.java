package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.spells.types.SteadyShot;
import com.eastvillage.emerald.unit.Unit;

import java.util.List;

public class Ranger implements UnitType {

    @Override
    public int getBaseMaxHealth() {
        return 65;
    }

    @Override
    public int getBaseDefense() {
        return 2;
    }

    @Override
    public int getBaseMagicDefense() {
        return 0;
    }

    @Override
    public int getBaseAttack() {
        return 18;
    }

    @Override
    public int getBaseStrength() {
        return 3;
    }

    @Override
    public int getBaseAgility() {
        return 10;
    }

    @Override
    public int getBaseMagicPower() {
        return 3;
    }

    @Override
    public int getBaseRange() {
        return 4;
    }

    @Override
    public int getBaseMovementSpeed() {
        return 2;
    }

    @Override
    public AbilityType[] getAbilities() {
        return new AbilityType[] {new SteadyShot()};
    }

    @Override
    public String getTypeName() {
        return "Ranger";
    }

    @Override
    public TextureRegion getTexture() {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.RANGER));
    }
}
