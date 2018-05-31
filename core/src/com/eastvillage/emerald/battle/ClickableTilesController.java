package com.eastvillage.emerald.battle;

import com.badlogic.gdx.graphics.Texture;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;

import java.util.Collection;
import java.util.HashSet;

public class ClickableTilesController implements BattlefieldInputListener {

    private Battlefield battlefield;
    private HashSet<Hex> clickables = new HashSet<>();
    private Hex hoveredHex = null;
    private GameObject hoverShadow;

    public ClickableTilesController(Battlefield battlefield) {
        this.battlefield = battlefield;

        hoverShadow = new GameObject();
        hoverShadow.addComponent(new TexRenderer(hoverShadow.transform, (Texture) EmeraldGame.getAsset(Assets.HOVER_SHADOW)));
    }

    /** Clear the set of clickable tiles. */
    public void clear() {
        clickables.clear();
        hideShadow();
    }

    /** Add a single clickable hex to the controller. */
    public void add(Hex hex) {
        clickables.add(hex);
    }

    /** Remove a single clickable hex from the controller. */
    public void remove(Hex hex) {
        clickables.remove(hex);
        if (hoveredHex == hex) {
            hideShadow();
        }
    }

    /** Add a collection of clickable hexes to the controller. */
    public void addAll(Collection<? extends Hex> col) {
        clickables.addAll(col);
    }

    /** Remove a collection of clickable hexes from the controller. */
    public void removeAll(Collection<?> col) {
        clickables.removeAll(col);
        if (col.contains(hoveredHex)) {
            hideShadow();
        }
    }

    /** Returns a copy of the set of clickable hexes. */
    public HashSet<Hex> getClickables() {
        return new HashSet<>(clickables);
    }

    /** Shows the shadow at given tile. */
    private void showShadow(Tile tile) {
        hoveredHex = tile.hex;
        hoverShadow.transform.setParent(tile.transform);
    }

    /** Hides the shadow. */
    private void hideShadow() {
        hoveredHex = null;
        hoverShadow.transform.setParent(null);
    }

    @Override
    public void onTileHoverBegin(Tile tile) {
        if (clickables.contains(tile.hex)) {
            showShadow(tile);
        } else {
            hideShadow();
        }
    }

    @Override
    public void onTileHoverEnd(Tile tile) {
        hideShadow();
    }

    @Override
    public void onTileClicked(Tile tile, int button) {

    }
}
