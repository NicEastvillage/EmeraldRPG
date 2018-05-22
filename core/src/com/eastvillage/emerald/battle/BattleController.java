package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.unit.Unit;

import java.util.HashSet;

public class BattleController implements BattlefieldInputListener, TurnQueueListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;

    private HashSet<Hex> reachableHexes;

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;
        turnController = new TurnController(battlefield.getAllUnits());
        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addListener(this);
        Gdx.input.setInputProcessor(inputProcessor);

        showValidMoves(turnController.current().getUnit());
    }

    private void showValidMoves(BattleUnit unit) {
        hideValidMoves();

        HexPather pather = new HexPather(battlefield.getPositionOf(unit), unit.getUnit().getMovementSpeed(),
                hex -> !battlefield.isWithin(hex) && battlefield.isOccupied(hex));

        reachableHexes = pather.getAllReachableHexes();

        for (Hex hex : reachableHexes) {
            battlefield.getTile(hex).showIndicators(true, false);
        }
    }

    private void hideValidMoves() {
        if (reachableHexes == null) return;
        for (Hex hex : reachableHexes) {
            battlefield.getTile(hex).showIndicators(false, false);
        }
    }

    @Override
    public void onTileHoverBegin(Tile tile) {

    }

    @Override
    public void onTileHoverEnd(Tile tile) {

    }

    @Override
    public void onTileClicked(Tile tile, int button) {

    }

    @Override
    public void onQueueCycle(TurnController turnController) {
        showValidMoves(turnController.current().getUnit());
    }

    @Override
    public void onQueueModified(TurnController turnController) {

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
