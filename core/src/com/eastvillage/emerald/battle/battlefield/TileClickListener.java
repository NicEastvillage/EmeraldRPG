package com.eastvillage.emerald.battle.battlefield;

/** A listener for tile clicks. Used by the {@link ClickableHighlightController}. */
public interface TileClickListener {
    boolean click(Tile tile, int button);
}
