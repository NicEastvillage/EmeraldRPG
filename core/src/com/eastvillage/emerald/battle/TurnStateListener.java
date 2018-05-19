package com.eastvillage.emerald.battle;

public interface TurnStateListener {

    void onTurnStateAny(Turn turn);
    void onTurnStateIdle(Turn turn);
    void onTurnStateSpecialSelecting(Turn turn);
    void onTurnStateSpecialTargeting(Turn turn);
    void onTurnStateEnd(Turn turn);
}
