package com.eastvillage.emerald.battle.battlefield;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ClickableHighlightRequest {

    private Set<Hex> region;
    private HighlightType type;
    private TileClickListener callback;

    public ClickableHighlightRequest(Set<Hex> region, HighlightType type, TileClickListener callback) {
        this.region = region;
        this.type = type;
        this.callback = callback;
    }

    public Set<Hex> getRegion() {
        return new HashSet<>(region);
    }

    public HighlightType getType() {
        return type;
    }

    public boolean callback(Tile tile, int button) {
        return callback.click(tile, button);
    }
}
