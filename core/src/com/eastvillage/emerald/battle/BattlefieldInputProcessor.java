package com.eastvillage.emerald.battle;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.ArrayList;

public class BattlefieldInputProcessor extends InputAdapter {

    private Battlefield battlefield;
    private OrthographicCamera camera;

    private ArrayList<BattlefieldInputListener> listeners;

    private Hex hoveredHex;

    private boolean isDown = false;
    private Hex downedHex;
    private int downButton = -1;

    public BattlefieldInputProcessor(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        this.camera = camera;
        listeners = new ArrayList<>();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Hex hex = mouseToHex(screenX, screenY);
        if (!hex.equals(hoveredHex) && battlefield.isWithin(hoveredHex)) {
            // hover end
            Tile tile = battlefield.getTile(hoveredHex);
            for (BattlefieldInputListener listener : listeners) {
                listener.onTileHoverEnd(tile);
            }
        }

        hoveredHex = hex;
        if (battlefield.isWithin(hoveredHex)) {
            // hover begin
            Tile tile = battlefield.getTile(hoveredHex);
            for (BattlefieldInputListener listener : listeners) {
                listener.onTileHoverBegin(tile);
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isDown = true;
        downedHex = mouseToHex(screenX, screenY);
        downButton = button;
        return battlefield.isWithin(downedHex);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Hex upHex = mouseToHex(screenX, screenY);
        if (isDown && downButton == button) {
            if (battlefield.isWithin(upHex) && upHex.equals(downedHex)) {
                // click
                Tile tile = battlefield.getTile(upHex);
                for (BattlefieldInputListener listener : listeners) {
                    listener.onTileClicked(tile, button);
                }
                return true;
            }
        }
        return false;
    }

    /** Coverts a mouse position to a Hex. */
    private Hex mouseToHex(float x, float y) {
        Vector3 worldPos = camera.unproject(new Vector3(x, y, 0));
        return battlefield.pointToHex(worldPos.x, worldPos.y);
    }

    /** Register a listener. */
    public void addListener(BattlefieldInputListener listener) {
        listeners.add(listener);
    }

    /** Remove a listener. Returns true if the listener is registered and now removed. */
    public boolean removeListener(BattlefieldInputListener listener) {
        return listeners.remove(listener);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
