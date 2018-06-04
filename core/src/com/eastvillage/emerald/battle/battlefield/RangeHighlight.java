package com.eastvillage.emerald.battle.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.Layers;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.engine.TransformTree;

import java.util.ArrayList;
import java.util.HashMap;

public class RangeHighlight {

    private final TextureRegion TEX_E, TEX_NE, TEX_NW, TEX_W, TEX_SW, TEX_SE;

    private Battlefield battlefield;
    private Pool<Highlight> highlightPool;
    private ArrayList<Highlight> activeHighlights = new ArrayList<>();
    private HashMap<HexDirection, TextureRegion> dirToTexMap;

    public RangeHighlight(Battlefield battlefield) {
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

        highlightPool = new Pool<Highlight>() {
            @Override
            protected Highlight newObject() {
                return new Highlight();
            }
        };
    }

    /** Display a range given a center and a range. */
    public void display(Hex center, int range) {

        clear();

        HexDirection[] dirs = HexDirection.values();
        Hex cur = center.add(dirs[4].asHex().scale(range));

        for (int i = 0; i < dirs.length; i++) {
            for (int j = 0; j < range; j++) {
                if (battlefield.isWithin(cur)) {
                    TransformTree<GameObject> tileTf = battlefield.getTile(cur).transform;

                    createHighlightForDirection(dirs, i - 1, tileTf);
                    createHighlightForDirection(dirs, i - 2, tileTf);

                    if (j == 0) {
                        createHighlightForDirection(dirs, i - 3, tileTf);
                    }
                }
                cur = cur.add(dirs[i].asHex());
            }
        }
    }

    /** Place a highlight at with the tile transform as parent, using the direction in dirs given by the index. */
    private void createHighlightForDirection(HexDirection[] dirs, int index, TransformTree<GameObject> tileTf) {
        index %= 6;
        if (index < 0) index += 6;

        Highlight highlight = highlightPool.obtain();
        highlight.init(tileTf, dirToTexMap.get(dirs[index]));
        activeHighlights.add(highlight);
    }

    /** Clears the currently highlighted range. */
    public void clear() {
        for (Highlight activeHighlight : activeHighlights) {
            highlightPool.free(activeHighlight);
        }
        activeHighlights.clear();
    }

    /** The Highlight is a struct-like class that holds a reference to the GameObject with a TexRenderer, that
     * displays the texture. It's a Poolable. */
    private class Highlight implements Pool.Poolable {

        private TransformTree<GameObject> transform;
        private TexRenderer renderer;

        public Highlight() {
            GameObject go = new GameObject();
            transform = go.transform;
            renderer = new TexRenderer(transform, (TextureRegion) null);
            renderer.setZ(Layers.RANGE_HIGHLIGHTS);
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
