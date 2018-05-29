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

    public BattleStage(BattleController controller) {
        super();

        Skin skin = new Skin();
        skin.addRegions(EmeraldGame.getAsset(Assets.GUI_ATLAS));
        skin.load(Gdx.files.internal(Assets.GUI_STYLE));
        root = new Table(skin);
        root.setFillParent(true);
        //root.setDebug(true);
        addActor(root);

        endTurnButton = new TextButton("End Turn", skin, "emerald");
        endTurnButton.setSize(150, 70);
        root.add(endTurnButton).padTop(20).size(150, 70);
        root.top();
    }
}
