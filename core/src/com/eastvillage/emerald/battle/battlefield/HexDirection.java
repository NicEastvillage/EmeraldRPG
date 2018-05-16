package com.eastvillage.emerald.battle.battlefield;

public enum HexDirection {
    E(1, 0),
    NE(0, 1),
    NW(-1, 1),
    W(-1, 0),
    SW(0, -1),
    SE(1, -1);

    private Hex hex;

    HexDirection(int x, int y) {
        this.hex = new Hex(x, y);
    }

    public Hex asHex() {
        return hex;
    }
}
