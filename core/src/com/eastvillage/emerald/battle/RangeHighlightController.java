package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.RangeHighlight;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.Collection;
import java.util.HashSet;

public class RangeHighlightController implements BattlefieldTileInputListener {

    private Battlefield battlefield;
    private HashSet<Hex> hexes = new HashSet<>();
    private Hex hoveredHex = null;
    private RangeHighlight rangeHighlight;
    private int range = 1;

    public RangeHighlightController(Battlefield battlefield) {
        this.battlefield = battlefield;
        rangeHighlight = new RangeHighlight(battlefield);
    }

    public void setRange(int r) {
        range = r;
    }

    /** Set the hexes where the range highlight should be displayed when hovered. */
    public void setHexes(Collection<? extends Hex> hexes) {
        this.hexes = new HashSet<>(hexes);
    }

    /** Add a hex that will display the range highlight. */
    public void addHex(Hex hex) {
        hexes.add(hex);
    }

    /** Clear the hexes that displays the range highlight. */
    public void clearHexes() {
        hexes.clear();
        rangeHighlight.clear();
    }

    @Override
    public void onTileHoverBegin(Tile tile) {
        if (hexes.contains(tile.hex)) {
            rangeHighlight.display(tile.hex, range);
        } else {
            rangeHighlight.clear();
        }
    }

    @Override
    public void onTileHoverEnd(Tile tile) {
        rangeHighlight.clear();
    }

    @Override
    public void onTileDragged(Tile prev, Tile now, int button) {

    }

    @Override
    public void onTileTouchDown(Tile tile, int button) {

    }

    @Override
    public void onTileTouchUp(Tile tile, int button) {

    }

    @Override
    public void onTileClicked(Tile tile, int button) {

    }
}
