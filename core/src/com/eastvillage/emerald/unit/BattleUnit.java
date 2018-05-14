package com.eastvillage.emerald.unit;

import com.eastvillage.emerald.battlefield.Tile;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.math.Vector2;

public class BattleUnit extends GameObject {

    private Unit unit;
    private Tile tile;

    public BattleUnit(Unit unit, Tile tile) {
        this.unit = unit;
        setTile(tile);

        addComponent(new TexRenderer(unit.getTexture(), false));
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        transform.setParent(tile.transform);
    }

    public Unit getUnit() {
        return unit;
    }
}
