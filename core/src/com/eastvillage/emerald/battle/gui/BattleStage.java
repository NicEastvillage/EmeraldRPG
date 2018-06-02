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
        setupTopRight(skin);
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

    private void setupTopRight(Skin skin) {
        topRightRoot = new Table(skin);
        topRightRoot.setFillParent(true);
        topRightRoot.setDebug(true);
        topRightRoot.top().right();
        topRightRoot.pad(20);
        addActor(topRightRoot);

        topRightRoot.columnDefaults(0).width(150);

        Image image = new Image(skin.getDrawable("portrait-frame"));
        topRightRoot.add(image).size(150, 150).spaceBottom(10).row();

        topRightRoot.add(new Label("Name of unit", skin)).spaceBottom(10).row();
        topRightRoot.add(new Label("Health: X/X", skin)).row();
        topRightRoot.add(new Label("Attack: X", skin)).row();
        topRightRoot.add(new Label("Defence: X", skin)).row();
        topRightRoot.add(new Label("Movement: X", skin)).row();
        topRightRoot.add(new Label("Range: X", skin)).row();
    }
}
