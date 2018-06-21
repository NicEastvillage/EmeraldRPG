package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.eastvillage.emerald.battle.SpellController;

public class SpellButton extends Button {

    private SpellController spellController;
    private int abilityIndex;

    public SpellButton(Skin skin, SpellController spellController, int abilityIndex) {
        super(skin, "sapphire");
        this.spellController = spellController;
        this.abilityIndex = abilityIndex;

        //setDebug(true);
    }

    public boolean isDisabled() {
        return false;
    }

    public void onClick(InputEvent event) {
        spellController.activate(abilityIndex);
    }
}
