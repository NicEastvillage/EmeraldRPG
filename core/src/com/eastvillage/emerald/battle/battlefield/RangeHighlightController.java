package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.engine.TransformTree;

public class RangeHighlightController {

    private final TextureRegion TEX_E, TEX_NE, TEX_NW, TEX_W, TEX_SW, TEX_SE;

    private Battlefield battlefield;
    private Pool<RangeHighlight> highlightPool;

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

        highlightPool = new Pool<RangeHighlight>() {
            @Override
            protected RangeHighlight newObject() {
                return new RangeHighlight();
            }
        };
    }

    /** The Range Highlight is a struct-like class that holds a reference to the GameObject with a TexRenderer, that
     * displays the texture. It's a Poolable. */
    private class RangeHighlight implements Pool.Poolable {

        private TransformTree<GameObject> transform;
        private TexRenderer renderer;

        public RangeHighlight() {
            GameObject go = new GameObject();
            transform = go.transform;
            TexRenderer renderer = new TexRenderer(transform, (TextureRegion) null);
            go.addComponent(renderer);
        }

        public void init(TransformTree<GameObject> tile, TextureRegion texReg) {
            this.transform.setParent(tile);
            renderer.setTex(texReg);
        }

        @Override
        public void reset() {
            transform.setParent(null);
        }
    }
}
