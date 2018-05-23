package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TransformTree;
import com.eastvillage.utility.math.Vector2;

public class UnitMover extends GameObject {

    private static final float DURATION = 0.5f;

    private BattleUnit unit;
    Vector2 startPos;
    Vector2 endPos;

    private float timePassed;

    /** Unit must be on start tile and will be move to destination tile */
    public UnitMover(TransformTree<GameObject> parent, BattleUnit unit, Battlefield battlefield, Tile destination) {
        super(parent);
        this.unit = unit;

        Tile startTile = battlefield.getTile(battlefield.getPositionOf(unit));

        battlefield.placeUnit(destination.hex, unit);

        startPos = startTile.transform.getWorldTransform().getPosition();
        startPos = destination.transform.getPointRelative(startPos);
        endPos = Vector2.ZERO;

        timePassed = 0;
    }

    @Override
    protected void onUpdate() {
        timePassed += Gdx.graphics.getDeltaTime();
        float t = Math.min(timePassed / DURATION, 1);

        //t = -2*t*t*t + 3*t*t; // ease in out quad
        t = t*(2-t); // ease out quad

        Vector2 pos = startPos.lerp(endPos, t);
        unit.transform.setLocalPosition(pos);

        if (t >= 1) {
            // Im done -> destroy
            transform.setParent(null);
            unit.transform.setLocalPosition(endPos);
        }
    }
}
