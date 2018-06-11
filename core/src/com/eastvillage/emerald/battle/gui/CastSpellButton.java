package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.eastvillage.emerald.battle.turn.Turn;
import com.eastvillage.emerald.battle.turn.TurnState;
import com.eastvillage.emerald.battle.turn.TurnStateListener;

import java.util.LinkedList;

public class CastSpellButton extends TextButton implements TurnStateListener {

    private static final String TXT_CAST = "Cast Spell";
    private static final String TXT_CANCEL = "Cancel";

    private LinkedList<CastSpellButtonListener> listeners = new LinkedList<>();
    private TurnState state = TurnState.IDLE;

    public CastSpellButton(Skin skin) {
        super(TXT_CAST, skin, "sapphire");
    }

    void onClick(InputEvent event) {
        switch (state) {
            case IDLE:
                listeners.forEach(CastSpellButtonListener::onCastSpellButtonStartSelecting);
                break;

            case SELECTING_SPECIAL:
                listeners.forEach(CastSpellButtonListener::onCastSpellButtonCancelSelecting);
                break;

            case SELECTING_SPECIAL_TARGET:
                listeners.forEach(CastSpellButtonListener::onCastSpellButtonCancelTargeting);
                break;
        }
    }

    public void addListener(CastSpellButtonListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(CastSpellButtonListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public void onTurnStateAny(Turn turn) {
        state = turn.getState();
    }

    @Override
    public void onTurnStateIdle(Turn turn) {
        setDisabled(false);
        setText(TXT_CAST);
    }

    @Override
    public void onTurnStateSpecialSelecting(Turn turn) {
        setDisabled(false);
        setText(TXT_CANCEL);
    }

    @Override
    public void onTurnStateSpecialTargeting(Turn turn) {
        setDisabled(false);
        setText(TXT_CANCEL);
    }

    @Override
    public void onTurnStateEnd(Turn turn) {
        setDisabled(true);
        setText(TXT_CAST);
    }
}
