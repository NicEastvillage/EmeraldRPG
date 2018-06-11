package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.eastvillage.emerald.battle.turn.Turn;
import com.eastvillage.emerald.battle.turn.TurnStateListener;

import java.util.LinkedList;

public class EndTurnButton extends TextButton implements TurnStateListener {

    private static final String TXT_END_TURN = "End Turn";
    private static final String TXT_SELECTING = "Select";
    private static final String TXT_WAITING = "Waiting";

    private LinkedList<EndTurnButtonListener> listeners = new LinkedList<>();

    public EndTurnButton(Skin skin) {
        super(TXT_END_TURN, skin, "emerald");
    }

    void onClick(InputEvent event) {
        listeners.forEach(EndTurnButtonListener::onEndTurnButtonPressed);
    }

    public void addListener(EndTurnButtonListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(EndTurnButtonListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public void onTurnStateAny(Turn turn) {

    }

    @Override
    public void onTurnStateIdle(Turn turn) {
        setDisabled(false);
        setText(TXT_END_TURN);
    }

    @Override
    public void onTurnStateSpecialSelecting(Turn turn) {
        setDisabled(true);
        setText(TXT_SELECTING);
    }

    @Override
    public void onTurnStateSpecialTargeting(Turn turn) {
        setDisabled(true);
        setText(TXT_SELECTING);
    }

    @Override
    public void onTurnStateEnd(Turn turn) {
        setDisabled(true);
        setText(TXT_WAITING);
    }
}
