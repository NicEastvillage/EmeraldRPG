package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.battle.BattleController;

public class BattleStage extends Stage {

    private BattleController controller;
    private Table root;
    private EndTurnButton endTurnButton;

    public BattleStage(BattleController controller, Skin skin) {
        super();

        this.controller = controller;

        root = new Table(skin);
        root.setFillParent(true);
        //root.setDebug(true);
        addActor(root);

        endTurnButton = new EndTurnButton("End Turn", skin, "emerald");
        controller.getTurnController().addTurnStateListener(endTurnButton);
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!endTurnButton.isDisabled())
                    controller.onEndTurnButtonPressed();
            }
        });
        root.add(endTurnButton).padTop(20).size(150, 70).fill();
        root.top();
    }
}
