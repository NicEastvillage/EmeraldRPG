package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TurnController {

    private LinkedList<BattleUnit> queue;
    private LinkedList<TurnQueueChangeListener> queueListeners;

    private TurnState state;
    private LinkedList<TurnStateListener> turnStateListeners;

    public TurnController(Collection<BattleUnit> units) {
        queue = new LinkedList<>(units);
        Collections.shuffle(queue);

        queueListeners = new LinkedList<>();
        turnStateListeners = new LinkedList<>();

        state = TurnState.IDLE;
    }

    public BattleUnit currentUnit() {
        return queue.getFirst();
    }

    public TurnState getState() {
        return state;
    }

    /** Returns the size of the turn queue. */
    public int queueSize() {
        return queue.size();
    }

    /** Cycles the queue, so that the first unit becomes the last unit. This method does not check the turn state.
     * @return the now current unit. */
    public BattleUnit endTurn() {
        BattleUnit cur = queue.pollFirst();
        queue.addLast(cur);

        state = TurnState.IDLE;

        updateQueueListeners();
        updateStateListerners();
        return currentUnit();
    }

    /** Removes a unit from the turn queue.
     * @return true, if the unit was in the queue and now removed. */
    public boolean remove(BattleUnit unit) {
        boolean remove = queue.remove(unit);
        updateQueueListeners();
        return remove;
    }

    public void addQueueListener(TurnQueueChangeListener listener) {
        queueListeners.add(listener);
    }

    /** Returns true, if the listener was registered and removed. */
    public boolean removeQueueListener(TurnQueueChangeListener listener) {
        return queueListeners.remove(listener);
    }

    public void addStateListener(TurnStateListener listener) {
        turnStateListeners.add(listener);
    }

    /** Returns true, if the listener was registered and removed. */
    public boolean removeStateListener(TurnStateListener listener) {
        return turnStateListeners.remove(listener);
    }

    /** Call this whenever the queue changes to update any listeners. */
    private void updateQueueListeners() {
        for (TurnQueueChangeListener listener : queueListeners) {
            listener.onQueueChange(this);
        }
    }

    /** Call this whenever the state changes to update any listeners. */
    private void updateStateListerners() {
        for (TurnStateListener listener : turnStateListeners) {
            listener.onTurnStateAny(this);
            switch (state) {
                case IDLE:
                    listener.onTurnStateIdle(this);
                    break;
                case IDLE_2:
                    listener.onTurnStateSecondIdle(this);
                    break;
                case SELECTING_SPECIAL:
                    listener.onTurnStateSpecialSelecting(this);
                    break;
                case SELECTING_SPECIAL_TARGET:
                    listener.onTurnStateSpecialTargeting(this);
                    break;
            }
        }
    }
}
