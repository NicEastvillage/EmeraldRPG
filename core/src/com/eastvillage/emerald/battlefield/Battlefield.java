package com.eastvillage.emerald.battlefield;

import com.eastvillage.emerald.unit.BattleUnit;
import com.eastvillage.emerald.unit.Knight;
import com.eastvillage.emerald.unit.Unit;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TransformTree;

import java.util.HashMap;

public class Battlefield extends GameObject {

    public static final int TILE_VERT = 13;
    public static final int TILE_HOR = 18;

    private HashMap<Hex, Tile> map = new HashMap<>();

    public Battlefield() {
        this(null);
    }

    public Battlefield(TransformTree<GameObject> parent) {
        super(parent);

        createTiles();

        new BattleUnit(new Unit(0, new Knight()), map.get(new Hex(4, 2)));
    }

    private void createTiles() {
        for (int y = 0; y < TILE_VERT; y++) {
            int tiles_hor = TILE_HOR + ((y & 1) == 0 ? 1 : 0);
            for (int x = 0; x < tiles_hor; x++) {
                int q = x - y / 2;
                int r = y;
                Hex hex = new Hex(q, r);
                Tile tile = new Tile(hex,transform);
                map.put(hex, tile);
            }
        }
    }
}
