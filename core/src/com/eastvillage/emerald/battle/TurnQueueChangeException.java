package com.eastvillage.emerald.battle;

/** Thrown when a unit can't be added or removed from the turn queue. */
public class TurnQueueChangeException extends RuntimeException {

    public TurnQueueChangeException() {
    }

    public TurnQueueChangeException(String message) {
        super(message);
    }
}
