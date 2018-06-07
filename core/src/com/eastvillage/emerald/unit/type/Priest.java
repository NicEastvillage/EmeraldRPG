package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.unit.Unit;

public class Priest implements UnitType {

    @Override
    public int getBaseMaxHealth() {
        return 18;
    }

    @Override
    public int getBaseDefense() {
        return 4;
    }

    @Override
    public int getBaseAttack() {
        return 3;
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
    public String getTypeName() {
        return "Priest";
    }

    @Override
    public TextureRegion getTexture() {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.PRIEST));
    }
}
