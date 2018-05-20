package com.eastvillage.emerald.battle;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

public class BattlefieldInputProcessor extends InputAdapter {

    private Battlefield battlefield;
    private OrthographicCamera camera;

    private Hex hoveredHex;
    private Tile hoveredTile;

    public BattlefieldInputProcessor(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        this.camera = camera;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldPos = camera.unproject(new Vector3((float)screenX, (float)screenY, 0));
        Hex hex = battlefield.pointToHex(worldPos.x, worldPos.y);
        if (!hex.equals(hoveredHex) && hoveredTile != null) {
            hoveredTile.showIndicators(true, false);
        }

        hoveredHex = hex;
        hoveredTile = battlefield.getTile(hoveredHex);
        if (hoveredTile != null) {
            battlefield.getTile(hoveredHex).showIndicators(false, true);
        }
        return false;
    }
}
