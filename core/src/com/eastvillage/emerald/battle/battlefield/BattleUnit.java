package com.eastvillage.emerald.battle.battlefield;

import com.eastvillage.emerald.Layers;
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

        texRenderer = new TexRenderer(transform, unit.getTexture());
        texRenderer.flipTex(allegiance.isSpriteFlipped(), false);
        texRenderer.setZ(Layers.UNITS);
        addComponent(texRenderer);
    }

    public Unit getUnit() {
        return unit;
    }

    public Allegiance getAllegiance() {
        return allegiance;
    }

    public boolean isAlly(BattleUnit unit) {
        return allegiance == unit.getAllegiance();
    }

    public boolean isEnemy(BattleUnit unit) {
        return allegiance != unit.getAllegiance();
    }
}