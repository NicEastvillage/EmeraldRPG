package com.eastvillage.emerald.battle;

@FunctionalInterface
public interface TurnQueueChangeListener {
    void onQueueChange(TurnController turnController);
}
