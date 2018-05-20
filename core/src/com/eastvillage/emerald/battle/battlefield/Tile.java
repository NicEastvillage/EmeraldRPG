package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.engine.TransformTree;
import com.eastvillage.utility.math.Vector2;

/** A Tile is a game object and is responsible for displaying the state of a hex. */
public class Tile extends GameObject {

    public static final int PIX_WIDTH = 32;
    public static final int PIX_HEIGHT = 36;
    public static final int SPACING_WIDTH = PIX_WIDTH;
    public static final int SPACING_HEIGHT = PIX_HEIGHT - 8;

    public final Hex hex;

    private TexRenderer baseTexRenderer;
    private TexRenderer indicatorTexRenderer;
    private TextureRegion indicatorValidMove, indicatorValidAttack;

    public Tile(Hex hex, TransformTree<GameObject> parent) {
        this.hex = hex;
        transform.setParent(parent);
        transform.setWorldPosition(new Vector2(hex.x * SPACING_WIDTH + hex.y * SPACING_WIDTH / 2, hex.y * SPACING_HEIGHT));

        TextureRegion baseTexture = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.GRASS));
        baseTexRenderer = new TexRenderer(baseTexture, false);
        addComponent(baseTexRenderer);

        indicatorValidMove = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.INDICATOR_MOVE));
        indicatorValidAttack = new TextureRegion((Texture) EmeraldGame.getAsset(Assets.INDICATOR_ATTACK));
        indicatorTexRenderer = new TexRenderer(indicatorValidMove, false);
        //indicatorTexRenderer.enable(false);
        addComponent(indicatorTexRenderer);
    }

    /** Toggle which indicator is shown. Only one indicator can be shown at once. */
    public void showIndicators(boolean move, boolean attack) {
        if (move && attack) throw new IllegalArgumentException("Only one indicator can be shown at once.");

        if (move) {
            indicatorTexRenderer.setTex(indicatorValidMove, false);
        } else if (attack) {
            indicatorTexRenderer.setTex(indicatorValidAttack, false);
        }

        indicatorTexRenderer.enable(move || attack);
    }
}
