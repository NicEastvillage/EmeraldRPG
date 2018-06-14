package com.eastvillage.emerald.battle.battlefield;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ClickableHighlightRequest {

    private Set<Hex> region;
    private HighlightType type;
    private Consumer<Tile> callback;

    public ClickableHighlightRequest(Set<Hex> region, HighlightType type, Consumer<Tile> callback) {
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

    public void callback(Tile tile) {
        callback.accept(tile);
    }
}
