package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;

import java.util.HashSet;
import java.util.function.Function;

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

        highlightValidMoves(turnController.current().getUnit());
    }

    /** Highlight the tiles that are valid moves for the given unit. The previously highlighted tiles will un-highlighted. */
    private void highlightValidMoves(BattleUnit unit) {
        hideValidMoves();

        Hex start = battlefield.getPositionOf(unit);
        int movement = unit.getUnit().getMovementSpeed();
        Function<Hex, Boolean> impassable = hex -> !battlefield.isWithin(hex) || battlefield.isOccupied(hex);
        HexPather pather = new HexPather(start, movement, impassable);

        pather.dijkstra();
        reachableHexes = pather.getAllReachableHexes();
        reachableHexes.remove(start);

        for (Hex hex : reachableHexes) {
            battlefield.getTile(hex).showIndicators(true, false);
        }
    }

    /** Remove the highlight from reachable tiles. */
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
        highlightValidMoves(turnController.current().getUnit());
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
