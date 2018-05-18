package com.eastvillage.emerald.battle.battlefield;

/** Thrown when trying to access a hex that is not within the bounds of the battlefield. */
public class NotWithinBattlefieldException extends RuntimeException {

    private Hex hex;

    public NotWithinBattlefieldException(Hex hex) {
        this.hex = hex;
    }

    public NotWithinBattlefieldException(Hex hex, String message) {
        super(message);
        this.hex = hex;
    }

    public Hex getHex() {
        return hex;
    }
}
