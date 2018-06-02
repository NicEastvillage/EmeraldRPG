package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eastvillage.emerald.battle.BattleController;

public class BattleStage extends Stage {

    private BattleController controller;
    private Table topRoot;
    private Table topRightRoot;
    private EndTurnButton endTurnButton;

    public BattleStage(BattleController controller, Skin skin) {
        super();

        this.controller = controller;

        setupTop(skin);

        topRightRoot = new HoverDetails(controller, skin);
        topRightRoot.setFillParent(true);
        topRightRoot.setDebug(true);
        topRightRoot.top().right();
        topRightRoot.pad(20);
        addActor(topRightRoot);
    }

    private void setupTop(Skin skin) {
        topRoot = new Table(skin);
        topRoot.setFillParent(true);
        //topRoot.setDebug(true);
        topRoot.top();
        topRoot.pad(20);
        addActor(topRoot);

        endTurnButton = new EndTurnButton("End Turn", skin, "emerald");
        controller.getTurnController().addTurnStateListener(endTurnButton);
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!endTurnButton.isDisabled())
                    controller.onEndTurnButtonPressed();
            }
        });
        topRoot.add(endTurnButton).size(150, 70);
    }
}
