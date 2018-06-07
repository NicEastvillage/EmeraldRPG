package com.eastvillage.emerald.unit.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.unit.Unit;

public class Knight implements UnitType {

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
        return 4;
    }

    @Override
    public int getRange(Unit unit) {
        return 1;
    }

    @Override
    public int getMovementSpeed(Unit unit) {
        return 3;
    }

    @Override
    public String getTypeName() {
        return "Knight";
    }

    @Override
    public TextureRegion getTexture(Unit unit) {
        return new TextureRegion((Texture) EmeraldGame.getAsset(Assets.KNIGHT));
    }
}
