package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.unit.Unit;

public class BattleController {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        turnController = new TurnController(battlefield.getAllUnits());
        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
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
}
