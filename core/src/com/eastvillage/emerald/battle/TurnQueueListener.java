package com.eastvillage.emerald.battle;

public interface TurnQueueListener {
    void onQueueCycle(TurnController turnController);
    void onQueueModified(TurnController turnController);
}
