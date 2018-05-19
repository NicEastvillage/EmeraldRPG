package com.eastvillage.emerald.battle;

/** Thrown when a turn is prompted to change to a state, which is not allowed. */
public class IllegalStateChangeException extends RuntimeException {

    public IllegalStateChangeException() {
    }

    public IllegalStateChangeException(String message) {
        super(message);
    }
}
