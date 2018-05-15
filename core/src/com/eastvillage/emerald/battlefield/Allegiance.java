package com.eastvillage.emerald.battlefield;

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
