package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class HighlightController {

    private TextureRegion highlightCurrentUnit;
    private TextureRegion highlightMove;
    private TextureRegion highlightAttack;

    private Battlefield battlefield;
    private HashMap<Hex, TextureRegion> highlights;
    private Hex currentUnit;
    private ArrayList<Hex> validMoves;
    private ArrayList<Hex> validAttacks;
    private HashSet<Hex> clickables;

    public HighlightController(Battlefield battlefield) {
        this.battlefield = battlefield;

        highlightCurrentUnit = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_CURRENT_UNIT));
        highlightMove = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_MOVE));
        highlightAttack = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.HIGHLIGHT_ATTACK));

        highlights = new HashMap<>();
        validMoves = new ArrayList<>();
        validAttacks = new ArrayList<>();
        clickables = new HashSet<>();
    }

    /** Clears all highlighting. */
    public void clearAll() {
        clearCurrentUnit();
        clearValidMoves();
        clearValidAttacks();
    }

    /** Set highlight of current unit. */
    public void setCurrentUnit(Hex hex) {
        clearCurrentUnit();
        currentUnit = hex;
        battlefield.getTile(currentUnit).showHighlight(highlightCurrentUnit);
    }

    /** Clear highlight of current unit. */
    public void clearCurrentUnit() {
        if (currentUnit != null) {
            battlefield.getTile(currentUnit).showHighlight(null);
        }
    }

    /** Set highlights of valid moves. */
    public void setValidMoves(Collection<Hex> validMoves) {
        clearValidMoves();
        this.validMoves = new ArrayList<>(validMoves);
        for (Hex validMove : validMoves) {
            battlefield.getTile(validMove).showHighlight(highlightMove);
        }
    }

    /** Clear highlights of valid moves. */
    public void clearValidMoves() {
        for (Hex validMove : validMoves) {
            battlefield.getTile(validMove).showHighlight(null);
        }
        validMoves.clear();
    }

    /** Set highlights of valid attacks. */
    public void setValidAttacks(Collection<Hex> validAttacks) {
        clearValidAttacks();
        this.validAttacks = new ArrayList<>(validAttacks);
        for (Hex validAttack : validAttacks) {
            battlefield.getTile(validAttack).showHighlight(highlightAttack);
        }
    }

    /** Clear highlights of valid attacks */
    public void clearValidAttacks() {
        for (Hex validAttack : validAttacks) {
            battlefield.getTile(validAttack).showHighlight(null);
        }
        validAttacks.clear();
    }
}
