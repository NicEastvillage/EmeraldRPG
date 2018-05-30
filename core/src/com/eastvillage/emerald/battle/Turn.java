package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.LinkedList;
import java.util.List;

/** The Turn class describes the state of current turn and variables relevant to it. */
public class Turn {

    private BattleUnit unit;
    private TurnController controller;
    private boolean hasMoved;
    private TurnState state;
    private LinkedList<TurnStateListener> turnStateListeners;

    public Turn(BattleUnit unit, TurnController controller, List<TurnStateListener> stateListeners) {
        this.unit = unit;
        this.controller = controller;
        state = TurnState.BEGINNING;
        hasMoved = false;
        turnStateListeners = new LinkedList<>();

        for (TurnStateListener listener : stateListeners) {
            addStateListener(listener);
        }
    }

    /** Call to indicate, that the unit has moved. */
    public void markHasMoved() {
        hasMoved = true;
    }

    /** Change the state of the turn. */
    public void changeState(TurnState state) {
        if (allowStateChange(state)) {
            this.state = state;
            updateStateListerners();
            if (state == TurnState.ENDED) {
                controller.onTurnEnd();
            }
        } else {
            throw new IllegalStateChangeException("The TurnState:" + this.state + " can not go to TurnState:" + state);
        }
    }

    /** Returns true if current state is allowed to change to the other state. */
    private boolean allowStateChange(TurnState other) {
        switch (state) {
            case BEGINNING:
                return other == TurnState.IDLE;
            case IDLE:
                return other == TurnState.SELECTING_SPECIAL
                    || other == TurnState.ENDED;

            case SELECTING_SPECIAL:
                return other == TurnState.IDLE
                    || other == TurnState.SELECTING_SPECIAL_TARGET;

            case SELECTING_SPECIAL_TARGET:
                return other == TurnState.SELECTING_SPECIAL
                    || other == TurnState.ENDED;

            case ENDED:
                return other == TurnState.ENDED;
            default:
                return false;
        }
    }

    /** TurnStateListeners will be call whenever the state changes. */
    public void addStateListener(TurnStateListener listener) {
        turnStateListeners.add(listener);
    }

    /** Returns true, if the listener was registered and removed. */
    public boolean removeStateListener(TurnStateListener listener) {
        return turnStateListeners.remove(listener);
    }

    /** Call this whenever the state changes to update any listeners. */
    private void updateStateListerners() {
        for (TurnStateListener listener : turnStateListeners) {
            listener.onTurnStateAny(this);
            switch (state) {
                case IDLE:
                    listener.onTurnStateIdle(this);
                    break;
                case SELECTING_SPECIAL:
                    listener.onTurnStateSpecialSelecting(this);
                    break;
                case SELECTING_SPECIAL_TARGET:
                    listener.onTurnStateSpecialTargeting(this);
                    break;
                case ENDED:
                    listener.onTurnStateEnd(this);
                    break;
            }
        }
    }

    public BattleUnit getUnit() {
        return unit;
    }

    public TurnState getState() {
        return state;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public TurnController getController() {
        return controller;
    }
}
