package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.eastvillage.emerald.battle.turn.Turn;
import com.eastvillage.emerald.battle.turn.TurnStateListener;

public class EndTurnButton extends TextButton implements TurnStateListener {

    private final String TXT_END_TURN = "End Turn";
    private final String TXT_SELECTING = "Select";
    private final String TXT_WAITING = "Waiting";

    public EndTurnButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
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
