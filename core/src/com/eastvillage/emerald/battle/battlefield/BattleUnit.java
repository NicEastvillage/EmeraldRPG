package com.eastvillage.emerald.battle.battlefield;

import com.eastvillage.emerald.battle.Allegiance;
import com.eastvillage.emerald.unit.Unit;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;

public class BattleUnit extends GameObject {

    private Unit unit;
    private Allegiance allegiance;

    private TexRenderer texRenderer;

    public BattleUnit(Unit unit, Allegiance allegiance) {
        this.unit = unit;
        this.allegiance = allegiance;

        texRenderer = new TexRenderer(transform, unit.getTexture(), false);
        texRenderer.flipTex(allegiance.isSpriteFlipped(), false);
        texRenderer.setZ(1);
        addComponent(texRenderer);
    }

    public Unit getUnit() {
        return unit;
    }
}
