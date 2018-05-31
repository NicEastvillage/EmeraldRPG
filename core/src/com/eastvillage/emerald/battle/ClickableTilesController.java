package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.Collection;
import java.util.HashSet;

public class ClickableTilesController implements BattlefieldInputListener {

    private Battlefield battlefield;
    private HashSet<Hex> clickables = new HashSet<>();
    private Hex hoveredHex = null;

    public ClickableTilesController(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /** Clear the set of clickable tiles. */
    public void clear() {
        clickables.clear();
    }

    /** Add a single clickable hex to the controller. */
    public void add(Hex hex) {
        clickables.add(hex);
    }

    /** Remove a single clickable hex from the controller. */
    public void remove(Hex hex) {
        clickables.remove(hex);
    }

    /** Add a collection of clickable hexes to the controller. */
    public void addAll(Collection<? extends Hex> col) {
        clickables.addAll(col);
    }

    /** Remove a collection of clickable hexes from the controller. */
    public void removeAll(Collection<?> col) {
        clickables.removeAll(col);
    }

    /** Returns a copy of the set of clickable hexes. */
    public HashSet<Hex> getClickables() {
        return new HashSet<>(clickables);
    }

    @Override
    public void onTileHoverBegin(Tile tile) {
        hoveredHex = tile.hex;
    }

    @Override
    public void onTileHoverEnd(Tile tile) {
        hoveredHex = null;
    }

    @Override
    public void onTileClicked(Tile tile, int button) {

    }
}
