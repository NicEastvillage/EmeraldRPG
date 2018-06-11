package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eastvillage.emerald.battle.BattleController;

public class BattleStage extends Stage {

    private BattleController controller;
    private Table topRoot, topLeft;
    private HoverDetails topRightRoot;
    private EndTurnButton endTurnButton;
    private CastSpellButton spellButton;

    public BattleStage(BattleController controller, Skin skin) {
        super();

        this.controller = controller;

        setupTop(skin);
        setupTopRight(skin);
        setupTopLeft(skin);
    }

    private void setupTopLeft(Skin skin) {
        topLeft = new Table(skin);
        topLeft.setFillParent(true);
        //topLeft.setDebug(true);
        topLeft.left().top();
        topLeft.pad(20);
        addActor(topLeft);

        spellButton = new CastSpellButton(skin);
        controller.getTurnController().addTurnStateListener(spellButton);
        topLeft.add(spellButton).size(150, 70);
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
