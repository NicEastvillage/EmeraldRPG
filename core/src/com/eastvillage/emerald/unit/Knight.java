package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight implements UnitType {

    private static final TextureRegion tex = new TextureRegion(new Texture("Knight.png"));

    @Override
    public int getMaxHealth(Unit unit) {
        return 30;
    }

    @Override
    public int getDefence(Unit unit) {
        return 3;
    }

    @Override
    public int getAttack(Unit unit) {
        return 3;
    }

    @Override
    public int getMovementSpeed(Unit unit) {
        return 3;
    }

    @Override
    public TextureRegion getTexture(Unit unit) {
        return tex;
    }
}
