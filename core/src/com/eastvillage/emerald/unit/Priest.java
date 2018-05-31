package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;

public class Priest implements UnitType {

    @Override
    public int getMaxHealth(Unit unit) {
        return 18;
    }

    @Override
    public int getDefence(Unit unit) {
        return 4;
    }

    @Override
    public int getAttack(Unit unit) {
        return 4;
    }

    @Override
    public int getRange(Unit unit) {
        return 2;
    }

    @Override
    public int getMovementSpeed(Unit unit) {
        return 3;
    }

    @Override
    public TextureRegion getTexture(Unit unit) {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.PRIEST));
    }
}
