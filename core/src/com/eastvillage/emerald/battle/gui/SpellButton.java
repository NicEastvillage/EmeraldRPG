package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.eastvillage.emerald.spells.Ability;

public class SpellButton extends Table {

    private Ability ability;

    public SpellButton(Skin skin, Ability ability) {
        super(skin);
        this.ability = ability;

        setDebug(true);
        add().size(70, 70);
    }
}
