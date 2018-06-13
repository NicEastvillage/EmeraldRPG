package com.eastvillage.emerald.battle.battlefield;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClickableHighlightRegion {

    private Set<Hex> region;
    private HighlightType type;

    public ClickableHighlightRegion(Set<Hex> region, HighlightType type) {
        this.region = region;
        this.type = type;
    }

    public Set<Hex> getRegion() {
        return new HashSet<>(region);
    }

    public HighlightType getType() {
        return type;
    }
}
