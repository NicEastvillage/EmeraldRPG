package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.eastvillage.emerald.battle.turn.Turn;
import com.eastvillage.emerald.battle.turn.TurnStateListener;

public class CastSpellButton extends TextButton implements TurnStateListener {

    private static final String TXT_CAST = "Cast Spell";
    private static final String TXT_CANCEL = "Cancel";

    public CastSpellButton(Skin skin) {
        super(TXT_CAST, skin, "sapphire");
    }

    @Override
    public void onTurnStateAny(Turn turn) {

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
