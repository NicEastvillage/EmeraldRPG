package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TurnController {

    private LinkedList<BattleUnit> queue;
    private LinkedList<TurnQueueListener> queueListeners;

    private Turn current;

    public TurnController(Collection<BattleUnit> units) {
        queue = new LinkedList<>(units);
        Collections.shuffle(queue);

        queueListeners = new LinkedList<>();

        current = new Turn(queue.getFirst(), this);
    }

    /** Starts the first turn by switching its state to IDLE. */
    public void start() {
        current.changeState(TurnState.IDLE);
    }

    public Turn current()  {
        return current;
    }

    /** Returns the size of the turn queue. */
    public int queueSize() {
        return queue.size();
    }

    /** Cycles the queue and starts next units turn. As a result first unit becomes the last unit in the queue.
     * This method does not check the turn state.
     * @return the now current unit. */
    public void cycleQueue() {
        BattleUnit curUnit = queue.pollFirst();
        queue.addLast(curUnit);

        current = new Turn(queue.getFirst(), this);

        updateQueueCycleListeners();
        current.changeState(TurnState.IDLE);
    }

    /** Adds a unit to the turn queue. The unit will be the last in the queue. */
    public void add(BattleUnit unit) {
        if (unit == null) throw new IllegalArgumentException("null cannot be added to turn queue.");
        queue.addLast(unit);
        updateQueueModifiedListeners();
    }

    /** Removes a unit from the turn queue.
     * @return true, if the unit was in the queue and now removed. */
    public boolean remove(BattleUnit unit) {
        if (current.getUnit() == unit) throw new TurnQueueChangeException("Can't remove unit from turn queue, because it is currently that unit's turn");
        boolean remove = queue.remove(unit);
        updateQueueModifiedListeners();
        return remove;
    }

    public void addQueueListener(TurnQueueListener listener) {
        queueListeners.add(listener);
    }

    /** Returns true, if the listener was registered and removed. */
    public boolean removeQueueListener(TurnQueueListener listener) {
        return queueListeners.remove(listener);
    }

    /** Call this whenever the queue changes to update any listeners. */
    private void updateQueueCycleListeners() {
        for (TurnQueueListener listener : queueListeners) {
            listener.onQueueCycle(this);
        }
    }

    /** Call this whenever the queue is modified to update any listeners. */
    private void updateQueueModifiedListeners() {
        for (TurnQueueListener listener : queueListeners) {
            listener.onQueueModified(this);
        }
    }

    /** Returns a copy of the turn queue. Changes to that list does not affect the turn controller. */
    public LinkedList<BattleUnit> getQueueCopy() {
        return new LinkedList<>(queue);
    }
}
