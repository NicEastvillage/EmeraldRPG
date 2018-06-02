package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.eastvillage.emerald.battle.BattleController;

public class HoverDetails extends Table {

    private BattleController controller;

    private Label labelName;
    private Label labelHealth;
    private Label labelAttack;
    private Label labelDefence;
    private Label labelMovement;
    private Label labelRange;

    public HoverDetails(BattleController controller, Skin skin) {
        super();
        this.controller = controller;

        columnDefaults(0).width(150);

        Image image = new Image(skin.getDrawable("portrait-frame"));
        add(image).size(150, 150).spaceBottom(10).row();

        labelName = new Label("Name of unit", skin);
        labelHealth = new Label("Health: X/X", skin);
        labelAttack = new Label("Attack: X-X", skin);
        labelDefence = new Label("Defence: X-X", skin);
        labelMovement = new Label("Movement: X", skin);
        labelRange = new Label("Range: X", skin);

        add(labelName).spaceBottom(10).row();
        add(labelHealth).row();
        add(labelAttack).row();
        add(labelDefence).row();
        add(labelMovement).row();
        add(labelRange).row();
    }
}
