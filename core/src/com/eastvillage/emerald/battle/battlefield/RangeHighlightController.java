package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;

public class RangeHighlightController {

    private final TextureRegion TEX_E, TEX_NE, TEX_NW, TEX_W, TEX_SW, TEX_SE;

    private Battlefield battlefield;

    public RangeHighlightController(Battlefield battlefield) {
        this.battlefield = battlefield;

        Texture texture = EmeraldGame.getAsset(Assets.HIGHLIGHT_RANGE);
        TEX_E = new TextureRegion(texture, 0, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);
        TEX_W = new TextureRegion(texture, 0, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);
        TEX_W.flip(true, false);

        TEX_SE = new TextureRegion(texture, Tile.PIX_WIDTH, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);
        TEX_NE = new TextureRegion(texture, Tile.PIX_WIDTH, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);
        TEX_SW = new TextureRegion(texture, Tile.PIX_WIDTH, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);
        TEX_NW = new TextureRegion(texture, Tile.PIX_WIDTH, 0, Tile.PIX_WIDTH, Tile.PIX_HEIGHT);

        TEX_NE.flip(false, true);
        TEX_SW.flip(true, false);
        TEX_NW.flip(true, true);
    }
}
