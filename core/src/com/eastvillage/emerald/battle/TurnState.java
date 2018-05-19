package com.eastvillage.emerald.battle;

public enum TurnState {
    IDLE,
    IDLE_2, // In idle two a unit has moved, and can't move again
    SELECTING_SPECIAL,
    SELECTING_SPECIAL_TARGET
}
