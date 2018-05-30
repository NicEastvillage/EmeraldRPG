package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.eastvillage.emerald.Assets;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.battle.BattleController;

public class BattleStage extends Stage {

    private BattleController controller;
    private Table root;
    private TextButton endTurnButton;

    public BattleStage(BattleController controller, Skin skin) {
        super();

        this.controller = controller;

        root = new Table(skin);
        root.setFillParent(true);
        //root.setDebug(true);
        addActor(root);

        endTurnButton = new TextButton("End Turn", skin, "emerald");
        root.add(endTurnButton).padTop(20).size(150, 70).fill();
        root.top();
    }
}
