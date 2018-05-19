package com.eastvillage.emerald.battle;

public interface TurnStateListener {

    void onTurnStateAny(TurnController turnController);
    void onTurnStateIdle(TurnController turnController);
    void onTurnStateSecondIdle(TurnController turnController);
    void onTurnStateSpecialSelecting(TurnController turnController);
    void onTurnStateSpecialTargeting(TurnController turnController);
}
