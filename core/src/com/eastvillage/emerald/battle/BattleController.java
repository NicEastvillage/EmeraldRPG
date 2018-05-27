package com.eastvillage.emerald.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;

import java.util.HashSet;
import java.util.function.Function;

public class BattleController implements BattlefieldInputListener, TurnQueueListener, TurnStateListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;
    private HighlightController highlightController;

    private HashSet<Hex> reachableHexes;

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;

        turnController = new TurnController(battlefield.getAllUnits());
        turnController.addQueueListener(this);
        turnController.current().addStateListener(this);

        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addListener(this);
        Gdx.input.setInputProcessor(inputProcessor);

        highlightController = new HighlightController(battlefield);

        turnController.start();
    }

    @Override
    public void onTileHoverBegin(Tile tile) {

    }

    @Override
    public void onTileHoverEnd(Tile tile) {

    }

    @Override
    public void onTileClicked(Tile tile, int button) {
        if (button == 0) {
            if (reachableHexes.contains(tile.hex)) {
                new UnitMover(battlefield.transform, turnController.current().getUnit(), battlefield, tile);
                turnController.cycleQueue();
            }
        }
    }

    @Override
    public void onQueueCycle(TurnController turnController) {
        turnController.current().addStateListener(this);
    }

    @Override
    public void onQueueModified(TurnController turnController) {

    }

    @Override
    public void onTurnStateAny(Turn turn) {

    }

    @Override
    public void onTurnStateIdle(Turn turn) {
        BattleUnit unit = turn.getUnit();
        highlightController.clearValidMoves();

        Hex start = battlefield.getPositionOf(unit);
        int movement = unit.getUnit().getMovementSpeed();
        Function<Hex, Boolean> impassable = hex -> !battlefield.isWithin(hex) || battlefield.isOccupied(hex);
        HexPather pather = new HexPather(start, movement, impassable);

        pather.dijkstra();
        reachableHexes = pather.getAllReachableHexes();
        reachableHexes.remove(start);

        highlightController.setCurrentUnit(start);
        highlightController.setValidMoves(reachableHexes);
    }

    @Override
    public void onTurnStateSpecialSelecting(Turn turn) {

    }

    @Override
    public void onTurnStateSpecialTargeting(Turn turn) {

    }

    @Override
    public void onTurnStateEnd(Turn turn) {

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
