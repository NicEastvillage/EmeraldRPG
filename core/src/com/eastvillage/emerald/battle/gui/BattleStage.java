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

    public BattleStage(BattleController controller) {
        super();

        Skin skin = new Skin();
        skin.addRegions(EmeraldGame.getAsset(Assets.GUI_ATLAS));
        skin.load(Gdx.files.internal(Assets.GUI_STYLE));
        root = new Table(skin);
        root.setFillParent(true);
        root.setDebug(true);
        addActor(root);

        root.add(new TextButton("Hello World", skin, "default"));

    }
}
