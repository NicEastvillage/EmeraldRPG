package com.eastvillage.emerald.battlefield;

import com.eastvillage.emerald.battlefield.Allegiance;
import com.eastvillage.emerald.battlefield.Tile;
import com.eastvillage.emerald.unit.Unit;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.math.Vector2;

public class BattleUnit extends GameObject {

    private Tile tile;
    private Unit unit;
    private Allegiance allegiance;

    private TexRenderer texRenderer;

    public BattleUnit(Tile tile, Unit unit, Allegiance allegiance) {
        setTile(tile);
        this.unit = unit;
        this.allegiance = allegiance;

        texRenderer = new TexRenderer(unit.getTexture(), false);
        texRenderer.flipTex(allegiance.isSpriteFlipped(), false);
        addComponent(texRenderer);
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        transform.setParent(tile.transform);
    }

    public Unit getUnit() {
        return unit;
    }
}
