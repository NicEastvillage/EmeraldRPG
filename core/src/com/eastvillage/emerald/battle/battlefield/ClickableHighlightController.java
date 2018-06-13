package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ClickableHighlightController {

    private TextureRegion highlightCurrentUnit;
    private TextureRegion highlightMove;
    private TextureRegion highlightAttack;

    private Battlefield battlefield;
    private HashMap<ClickableHighlightRegion, Consumer<Hex>> requests;

    public ClickableHighlightController(Battlefield battlefield) {
        this.battlefield = battlefield;
        requests = new HashMap<>();

        highlightCurrentUnit = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_CURRENT_UNIT));
        highlightMove = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_MOVE));
        highlightAttack = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_ATTACK));
    }

    public ClickableHighlightRegion request(Set<Hex> hexes, HighlightType type, Consumer<Hex> callback) {
        ClickableHighlightRegion region = new ClickableHighlightRegion(new HashSet<>(hexes), type);
        requests.put(region, callback);
        displayHighlights(hexes, type);
        return region;
    }

    private void displayHighlights(Set<Hex> hexes, HighlightType type) {
        for (Hex hex : hexes) {
            battlefield.getTile(hex).showHighlight(getHighlightTexture(type));
        }
    }

    private TextureRegion getHighlightTexture(HighlightType type) {
        switch (type) {
            case CURRENT_UNIT: return highlightCurrentUnit;
            case VALID_MOVE: return highlightMove;
            case VALID_ATTACK: return highlightAttack;
            default: return null;
        }
    }
}
