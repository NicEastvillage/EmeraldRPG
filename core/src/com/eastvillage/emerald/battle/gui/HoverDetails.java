package com.eastvillage.emerald.battle.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.BattlefieldUnitInputListener;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.emerald.unit.Unit;

public class HoverDetails extends Table implements BattlefieldUnitInputListener {

    private BattleController controller;

    private BattleUnit hoveredUnit;

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

        labelName.setAlignment(Align.center);

        add(labelName).spaceBottom(10).row();
        add(labelHealth).row();
        add(labelAttack).row();
        add(labelDefence).row();
        add(labelMovement).row();
        add(labelRange).row();

        setVisible(false);
    }

    public void showDetails(BattleUnit bUnit) {
        Unit unit = bUnit.getUnit();
        labelName.setText(unit.getType().getTypeName());
        labelHealth.setText(String.format("Health: %d/%d", unit.getCurrentHealth(), unit.getMaxHealth().getValue()));
        labelAttack.setText(String.format("Attack: %d", unit.getAttack().getValue()));
        labelDefence.setText(String.format("Defence: %d", unit.getDefense().getValue()));
        labelMovement.setText(String.format("Movement: %d", unit.getMovementSpeed().getValue()));
        labelRange.setText(String.format("Range: %d", unit.getRange().getValue()));
    }

    @Override
    public void onUnitHoverBegin(Tile tile, BattleUnit unit) {
        hoveredUnit = unit;
        showDetails(unit);
        setVisible(true);
    }

    @Override
    public void onUnitHoverEnd(Tile tile, BattleUnit unit) {
        hoveredUnit = null;
        setVisible(false);
    }

    @Override
    public void onUnitTouchDown(Tile tile, BattleUnit unit, int button) {

    }

    @Override
    public void onUnitTouchUp(Tile tile, BattleUnit unit, int button) {

    }

    @Override
    public void onUnitClicked(Tile tile, BattleUnit unit, int button) {

    }
}
