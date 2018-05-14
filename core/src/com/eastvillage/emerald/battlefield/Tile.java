package com.eastvillage.emerald.battlefield;

import com.badlogic.gdx.graphics.Texture;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.engine.TransformTree;
import com.eastvillage.math.Vector2;

public class Tile extends GameObject {

    public static final int PIX_WIDTH = 32;
    public static final int PIX_HEIGHT = 36;
    public static final int SPACING_WIDTH = PIX_WIDTH;
    public static final int SPACING_HEIGHT = PIX_HEIGHT - 8;

    private static final Texture texture = new Texture("grass.png");

    public final Hex hex;

    public Tile(Hex hex, TransformTree<GameObject> parent) {
        this.hex = hex;
        transform.setParent(parent);
        transform.setWorldPosition(new Vector2(hex.x * SPACING_WIDTH + hex.y * SPACING_WIDTH / 2, hex.y * SPACING_HEIGHT));
        addComponent(new TexRenderer(texture, false));
    }
}
