package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TurnController {

    private LinkedList<BattleUnit> queue;
    private LinkedList<TurnQueueChangeListener> listeners;

    public TurnController(Collection<BattleUnit> units) {
        queue = new LinkedList<>(units);
        Collections.shuffle(queue);

        listeners = new LinkedList<>();
    }

    public BattleUnit current() {
        return queue.getFirst();
    }

    public BattleUnit next() {
        BattleUnit cur = queue.pollFirst();
        queue.addLast(cur);
        updateListeners();
        return queue.getFirst();
    }

    public boolean remove(BattleUnit unit) {
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
