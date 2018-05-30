package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TurnController implements TurnEndListener {

    private LinkedList<BattleUnit> queue;
    private LinkedList<TurnQueueListener> queueListeners;
    private LinkedList<TurnStateListener> turnStateListeners;
    private TurnEndListener turnEndListener;

    private Turn current;

    public TurnController(Collection<BattleUnit> units) {
        queue = new LinkedList<>(units);
        Collections.shuffle(queue);

        queueListeners = new LinkedList<>();
        turnStateListeners = new LinkedList<>();

        current = new Turn(queue.getFirst(), this, turnStateListeners);
    }

    /** The TurnEndListener will be the final call when a turn is ended. This will typically be a controller that
     * checks if the TurnController should cycle. */
    public void setTurnEndListener(TurnEndListener listener) {
        turnEndListener = listener;
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

        current = new Turn(queue.getFirst(), this, turnStateListeners);

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

    /** Add a TurnStateListener that will be trigger when the turn state changes in any turn. If the listener should
     * only listen to the current turn, add it directly to the current turn instead. */
    public void addTurnStateListener(TurnStateListener listener) {
        turnStateListeners.add(listener);
    }

    /** Remove a TurnStateListener. The listener will still receive calls from the current turn, unless it removed
     * from that turn as well. */
    public boolean removeTurnStateListener(TurnStateListener listener) {
        return turnStateListeners.remove(listener);
    }

    /** Returns a copy of the turn queue. Changes to that list does not affect the turn controller. */
    public LinkedList<BattleUnit> getQueueCopy() {
        return new LinkedList<>(queue);
    }

    @Override
    public void onTurnEnd() {
        if (turnEndListener != null)
            turnEndListener.onTurnEnd();
    }
}
