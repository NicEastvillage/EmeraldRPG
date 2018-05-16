package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.unit.Unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TurnController {

    private LinkedList<Unit> queue;
    private LinkedList<TurnQueueChangeListener> listeners;

    public TurnController(Collection<Unit> units) {
        queue = new LinkedList<>(units);
        Collections.shuffle(queue);

        listeners = new LinkedList<>();
    }

    public Unit current() {
        return queue.getFirst();
    }

    public Unit next() {
        Unit cur = queue.pollFirst();
        queue.addLast(cur);
        updateListeners();
        return queue.getFirst();
    }

    public boolean remove(Unit unit) {
        boolean remove = queue.remove(unit);
        updateListeners();
        return remove;
    }

    private void updateListeners() {
        for (TurnQueueChangeListener listener : listeners) {
            listener.onQueueChange(this);
        }
    }
}
