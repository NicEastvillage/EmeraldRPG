package com.eastvillage.emerald.battle;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.ArrayList;

public class BattlefieldInputProcessor extends InputAdapter {

    private Battlefield battlefield;
    private OrthographicCamera camera;

    private ArrayList<BattlefieldTileInputListener> tileInputListeners;
    private ArrayList<BattlefieldUnitInputListener> unitInputListeners;

    private Hex hoveredHex;

    private boolean isDown = false;
    private Hex downedHex;
    private int downButton = -1;
    private Hex dragPrevHex;
    private boolean dragHappened = false;

    public BattlefieldInputProcessor(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        this.camera = camera;
        tileInputListeners = new ArrayList<>();
        unitInputListeners = new ArrayList<>();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Hex hex = mouseToHex(screenX, screenY);
        if (!hex.equals(hoveredHex) && battlefield.isWithin(hoveredHex)) {
            // hover end
            Tile tile = battlefield.getTile(hoveredHex);
            BattleUnit unit = battlefield.getUnitAt(hoveredHex);
            for (BattlefieldTileInputListener listener : tileInputListeners) {
                listener.onTileHoverEnd(tile);
            }
            if (unit != null) {
                for (BattlefieldUnitInputListener listener : unitInputListeners) {
                    listener.onUnitHoverEnd(tile, unit);
                }
            }
        }

        hoveredHex = hex;
        if (battlefield.isWithin(hoveredHex)) {
            // hover begin
            Tile tile = battlefield.getTile(hoveredHex);
            BattleUnit unit = battlefield.getUnitAt(hoveredHex);
            for (BattlefieldTileInputListener listener : tileInputListeners) {
                listener.onTileHoverBegin(tile);
            }
            if (unit != null) {
                for (BattlefieldUnitInputListener listener : unitInputListeners) {
                    listener.onUnitHoverBegin(tile, unit);
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isDown = true;
        dragHappened = false;
        downedHex = mouseToHex(screenX, screenY);
        dragPrevHex = downedHex;
        downButton = button;
        if (battlefield.isWithin(downedHex)) {
            Tile tile = battlefield.getTile(downedHex);
            BattleUnit unit = battlefield.getUnitAt(downedHex);
            for (BattlefieldTileInputListener listener : tileInputListeners) {
                listener.onTileTouchDown(tile, button);
            }
            if (unit != null) {
                for (BattlefieldUnitInputListener listener : unitInputListeners) {
                    listener.onUnitTouchDown(tile, unit, button);
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Hex upHex = mouseToHex(screenX, screenY);
        if (isDown && downButton == button) {
            if (battlefield.isWithin(upHex)) {
                Tile tile = battlefield.getTile(upHex);
                BattleUnit unit = battlefield.getUnitAt(upHex);

                // Touch up
                for (BattlefieldTileInputListener listener : tileInputListeners) {
                    listener.onTileTouchUp(tile, button);
                }
                if (unit != null) {
                    for (BattlefieldUnitInputListener listener : unitInputListeners) {
                        listener.onUnitTouchUp(tile, unit, button);
                    }
                }

                if (!dragHappened && upHex.equals(downedHex)) {
                    // click
                    for (BattlefieldTileInputListener listener : tileInputListeners) {
                        listener.onTileClicked(tile, button);
                    }
                    if (unit != null) {
                        for (BattlefieldUnitInputListener listener : unitInputListeners) {
                            listener.onUnitClicked(tile, unit, button);
                        }
                    }
                }

                // Do a move to the new pos
                mouseMoved(screenX, screenY);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDown) {
            Hex dragHex = mouseToHex(screenX, screenY);
            if (!dragPrevHex.equals(dragHex)) {
                // Drag
                Tile prev = battlefield.getTile(dragPrevHex);
                Tile tile = battlefield.getTile(dragHex);
                dragHappened = true;
                for (BattlefieldTileInputListener listener : tileInputListeners) {
                    listener.onTileDragged(prev, tile, downButton);
                }
                dragPrevHex = dragHex;
            }
            return true;
        }
        return false;
    }

    /** Coverts a mouse position to a Hex. */
    private Hex mouseToHex(float x, float y) {
        Vector3 worldPos = camera.unproject(new Vector3(x, y, 0));
        return battlefield.pointToHex(worldPos.x, worldPos.y);
    }

    /** Register a tile input listener. */
    public void addTileInputListener(BattlefieldTileInputListener listener) {
        tileInputListeners.add(listener);
    }

    /** Register a unit input listener. */
    public void addUnitInputListener(BattlefieldUnitInputListener listener) {
        unitInputListeners.add(listener);
    }

    /** Remove a tile listener. Returns true if the listener is registered and now removed. */
    public boolean removeTileInputListener(BattlefieldTileInputListener listener) {
        return tileInputListeners.remove(listener);
    }

    /** Remove a unit listener. Returns true if the listener is registered and now removed. */
    public boolean removeUnitInputListener(BattlefieldUnitInputListener listener) {
        return unitInputListeners.remove(listener);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
