package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.SpellController;

public class BattleStage extends Stage {

    private static final int SPELL_BUTTON_COUNT = 3;

    private BattleController controller;
    private Table topRoot, topLeft;
    private HoverDetails topRightRoot;
    private EndTurnButton endTurnButton;
    private SpellButton[] spellButtons;

    public BattleStage(BattleController controller, Skin skin) {
        super();

        this.controller = controller;

        setupTop(skin);
        setupTopRight(skin);
        setupTopLeft(skin, controller.getSpellController());
    }

    private void setupTopLeft(Skin skin, SpellController spellController) {
        topLeft = new Table(skin);
        topLeft.setFillParent(true);
        //topLeft.setDebug(true);
        topLeft.left().top();
        topLeft.pad(20);
        addActor(topLeft);

        spellButtons = new SpellButton[SPELL_BUTTON_COUNT];
        for (int i = 0; i < SPELL_BUTTON_COUNT; i++) {
            SpellButton button = new SpellButton(skin, spellController, i);
            //controller.getTurnController().addTurnStateListener(spellButtons[i]);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!button.isDisabled())
                        button.onClick(event);
                }
            });
            topLeft.add(button).size(70, 70).spaceBottom(16).row();

            spellButtons[i] = button;
        }

    }

    private void setupTop(Skin skin) {
        topRoot = new Table(skin);
        topRoot.setFillParent(true);
        //topRoot.setDebug(true);
        topRoot.top();
        topRoot.pad(20);
        addActor(topRoot);

        endTurnButton = new EndTurnButton(skin);
        controller.getTurnController().addTurnStateListener(endTurnButton);
        endTurnButton.addListener(controller);
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!endTurnButton.isDisabled())
                    endTurnButton.onClick(event);
            }
        });
        topRoot.add(endTurnButton).size(150, 70);
    }

    private void setupTopRight(Skin skin) {
        topRightRoot = new HoverDetails(controller, skin);
        topRightRoot.setFillParent(true);
        //topRightRoot.setDebug(true);
        topRightRoot.top().right();
        topRightRoot.pad(20);
        addActor(topRightRoot);
        controller.getInputProcessor().addUnitInputListener(topRightRoot);
    }
}
