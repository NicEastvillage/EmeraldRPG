package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.emerald.unit.Unit;

public class BattleController implements BattlefieldInputListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        turnController = new TurnController(battlefield.getAllUnits());
        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addListener(this);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public BattlefieldInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public void onTileHoverBegin(Tile tile) {
        tile.showIndicators(false, true);
    }

    @Override
    public void onTileHoverEnd(Tile tile) {
        tile.showIndicators(false, false);
    }

    @Override
    public void onTileClicked(Tile tile, int button) {
        tile.showIndicators(true, false);
    }
}
