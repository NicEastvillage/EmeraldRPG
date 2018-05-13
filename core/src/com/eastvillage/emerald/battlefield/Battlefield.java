package com.eastvillage.emerald.battlefield;

import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TransformTree;

import java.util.HashMap;

public class Battlefield extends GameObject {

    private HashMap<Hex, Tile> map = new HashMap<>();

    public Battlefield() {
        this(null);
    }

    public Battlefield(TransformTree<GameObject> parent) {
        super(parent);

        new Tile(new Hex(1, 1), transform);
        new Tile(new Hex(2, 1), transform);
        new Tile(new Hex(1, 2), transform);
    }
}
