package com.eastvillage.emerald.battle.turn;

public interface TurnQueueListener {
    void onQueueCycle(TurnController turnController);
    void onQueueModified(TurnController turnController);
}
