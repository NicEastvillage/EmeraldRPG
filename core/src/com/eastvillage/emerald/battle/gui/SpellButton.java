package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.eastvillage.emerald.battle.SpellController;
import com.eastvillage.emerald.battle.SpellControllerListener;

public class SpellButton extends Button implements SpellControllerListener {

    private SpellController spellController;
    private int abilityIndex;

    public SpellButton(Skin skin, SpellController spellController, int abilityIndex) {
        super(skin, "sapphire");
        this.spellController = spellController;
        this.abilityIndex = abilityIndex;
        spellController.addListener(this);

        //setDebug(true);
    }

    public void onClick(InputEvent event) {
        spellController.activate(abilityIndex);
    }

    @Override
    public void onCurrentAbilitiesChanged(SpellController controller) {
        setDisabled(abilityIndex >= controller.abilityCount());
    }

    @Override
    public void onSpellSelected(SpellController controller, int index) {
        if (index == abilityIndex) {
            // that's me!
            setChecked(true);
        }
    }

    @Override
    public void onSpellDeselected(SpellController controller, int index) {
        if (index == abilityIndex) {
            // that's me!
            setChecked(false);
        }
    }

    @Override
    public void afterSpellCast(SpellController controller, int index) {
        if (index == abilityIndex) {
            // that's me!
            setChecked(false);
        }
    }
}
