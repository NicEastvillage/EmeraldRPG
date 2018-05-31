package com.eastvillage.emerald.battle;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;

import java.util.HashSet;
import java.util.function.Function;

public class BattleController implements BattlefieldInputListener, TurnQueueListener, TurnStateListener, TurnEndListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;
    private HighlightController highlightController;

    private HashSet<Hex> possibleMoveHexes = new HashSet<>();

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;

        turnController = new TurnController(battlefield.getAllUnits());
        turnController.addQueueListener(this);
        turnController.current().addStateListener(this); // first turn must be added directly
        turnController.addTurnStateListener(this);
        turnController.setTurnEndListener(this);

        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addListener(this);

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
            if (possibleMoveHexes.contains(tile.hex)) {
                new UnitMover(battlefield.transform, turnController.current().getUnit(), battlefield, tile);
                turnController.current().markHasMoved();
                turnController.current().changeState(TurnState.IDLE);
            }
        }
    }

    @Override
    public void onQueueCycle(TurnController turnController) {

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

        Hex pos = battlefield.getPositionOf(unit);
        highlightController.setCurrentUnit(pos);

        findPossibleMoves(turn, pos);
    }

    /** Find possible move of possible in the this turn state. Possible moves will be stored in {@code possibleMoveHexes}
     * and the tiles will be highlighted. */
    private void findPossibleMoves(Turn turn, Hex start) {
        if (!turn.hasMoved()) {

            int movement = turn.getUnit().getUnit().getMovementSpeed();
            Function<Hex, Boolean> impassable = hex -> !battlefield.isWithin(hex) || battlefield.isOccupied(hex);
            HexPather pather = new HexPather(start, movement, impassable);

            pather.dijkstra();
            possibleMoveHexes = pather.getAllReachableHexes();
            possibleMoveHexes.remove(start);

            highlightController.setValidMoves(possibleMoveHexes);
        }
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

    @Override
    public void onTurnEnd() {
        turnController.cycleQueue();
    }

    public void onEndTurnButtonPressed() {
        turnController.current().changeState(TurnState.ENDED);
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
