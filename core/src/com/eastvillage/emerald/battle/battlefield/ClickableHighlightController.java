package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.Layers;
import com.eastvillage.emerald.battle.BattlefieldTileInputListener;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;

import java.util.*;
import java.util.function.Consumer;

public class ClickableHighlightController implements BattlefieldTileInputListener {

    private TextureRegion highlightCurrentUnit;
    private TextureRegion highlightMove;
    private TextureRegion highlightAttack;

    private Battlefield battlefield;
    private HashMap<ClickableHighlightRegion, Consumer<Tile>> requests;
    private GameObject hoverShadow;
    private Hex hoveredHex = null;

    public ClickableHighlightController(Battlefield battlefield) {
        this.battlefield = battlefield;
        requests = new HashMap<>();

        hoverShadow = new GameObject();
        hoverShadow.addComponent(new TexRenderer(hoverShadow.transform, (Texture) EmeraldGame.getAsset(Assets.HOVER_SHADOW)).setZ(Layers.SHADOWS));

        highlightCurrentUnit = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_CURRENT_UNIT));
        highlightMove = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_MOVE));
        highlightAttack = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_ATTACK));
    }

    /** Request a click on one of the given hexes using the type of highlight given. The callback method will be called
     * when the click happens, and the highlight will disappear afterwards. The request can also be cancelled by
     * calling {@link #remove(ClickableHighlightRegion)}.*/
    public ClickableHighlightRegion request(Set<Hex> hexes, HighlightType type, Consumer<Tile> callback) {
        ClickableHighlightRegion region = new ClickableHighlightRegion(new HashSet<>(hexes), type);
        requests.put(region, callback);
        displayHighlights(region);
        return region;
    }

    /** Remove a request by given the region created upon request. */
    public void remove(ClickableHighlightRegion region) {
        requests.remove(region);
        updateAllHighlights();
    }

    /** Remove all requests. */
    public void clear() {
        requests.clear();
        hideShadow();
        updateAllHighlights();
    }

    public void updateAllHighlights() {
        for (Tile tile : battlefield) {
            tile.showHighlight(null);
        }
        for (ClickableHighlightRegion region : requests.keySet()) {
            displayHighlights(region);
        }
    }

    private void displayHighlights(ClickableHighlightRegion region) {
        Set<Hex> hexes = region.getRegion();
        TextureRegion tex = getHighlightTexture(region.getType());
        for (Hex hex : hexes) {
            battlefield.getTile(hex).showHighlight(tex);
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

    /** Returns true if given hex is clickable and has a highlight and callback attached. */
    public boolean isClickable(Hex hex) {
        for (ClickableHighlightRegion region : requests.keySet()) {
            for (Hex rhex : region.getRegion()) {
                if (rhex.equals(hex)) return true;
            }
        }
        return false;
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
        if (isClickable(tile.hex)) {
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
        boolean anyFulfilled = false;
        List<ClickableHighlightRegion> requestsFulfilled = new ArrayList<>();
        for (ClickableHighlightRegion region : requests.keySet()) {
            Set<Hex> hexes = region.getRegion();
            for (Hex hex : hexes) {
                Tile clicked = battlefield.getTile(hex);
                if (clicked == tile) {
                    requests.get(region).accept(clicked);
                    requestsFulfilled.add(region);
                    anyFulfilled = true;
                }
            }
        }

        if (anyFulfilled) {
            for (ClickableHighlightRegion region : requestsFulfilled) {
                requests.remove(region);
            }
            updateAllHighlights();
        }
    }
}
