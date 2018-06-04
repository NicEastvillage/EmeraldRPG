package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.engine.TransformTree;

import java.util.ArrayList;
import java.util.HashMap;

public class RangeHighlightController {

    private final TextureRegion TEX_E, TEX_NE, TEX_NW, TEX_W, TEX_SW, TEX_SE;

    private Battlefield battlefield;
    private Pool<RangeHighlight> highlightPool;
    private ArrayList<RangeHighlight> activeHighlights;
    private HashMap<HexDirection, TextureRegion> dirToTexMap;

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

        dirToTexMap = new HashMap<>();
        dirToTexMap.put(HexDirection.E, TEX_E);
        dirToTexMap.put(HexDirection.NE, TEX_NE);
        dirToTexMap.put(HexDirection.NW, TEX_NW);
        dirToTexMap.put(HexDirection.W, TEX_W);
        dirToTexMap.put(HexDirection.SW, TEX_SW);
        dirToTexMap.put(HexDirection.SE, TEX_SE);

        highlightPool = new Pool<RangeHighlight>() {
            @Override
            protected RangeHighlight newObject() {
                return new RangeHighlight();
            }
        };
    }

    public void display(Hex center, int range) {

        HexDirection[] dirs = HexDirection.values();
        Hex cur = center.add(dirs[0].asHex().scale(range));

        for (int i = 0; i < dirs.length; i++) {
            for (int j = 0; j < range; j++) {

                TransformTree<GameObject> tileTf = battlefield.getTile(cur).transform;

                // Current direction minus 1
                HexDirection dir1 = dirs[(j + 5) % 6];
                RangeHighlight highlight1 = highlightPool.obtain();
                highlight1.init(tileTf, dirToTexMap.get(dir1));
                activeHighlights.add(highlight1);

                // Current direction minus 2
                HexDirection dir2 = dirs[(j + 4) % 6];
                RangeHighlight highlight2 = highlightPool.obtain();
                highlight2.init(tileTf, dirToTexMap.get(dir2));
                activeHighlights.add(highlight2);

                if (j == 0) {
                    // Current direction minus 3
                    HexDirection dir3 = dirs[(j + 3) % 6];
                    RangeHighlight highlight3 = highlightPool.obtain();
                    highlight2.init(tileTf, dirToTexMap.get(dir3));
                    activeHighlights.add(highlight3);
                }

                cur = cur.add(dirs[i].asHex());
            }
        }
    }

    /** Hides the current highlighted range. */
    public void hide() {
        for (RangeHighlight activeHighlight : activeHighlights) {
            highlightPool.free(activeHighlight);
        }
        activeHighlights.clear();
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
