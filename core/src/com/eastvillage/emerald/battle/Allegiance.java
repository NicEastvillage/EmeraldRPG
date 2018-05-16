package com.eastvillage.emerald.battle;

public enum Allegiance {
    LEFT(false),
    RIGHT(true),
    OTHER(false);

    private boolean spriteFlipped;

    Allegiance(boolean spriteFlipped) {
        this.spriteFlipped = spriteFlipped;
    }

    public boolean isSpriteFlipped() {
        return spriteFlipped;
    }
}
