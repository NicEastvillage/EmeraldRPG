package com.eastvillage.emerald.battle;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.battle.turn.*;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

public class BattleController implements BattlefieldTileInputListener, TurnQueueListener, TurnStateListener, TurnEndListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;
    private HighlightController highlightController;
    private RangeHighlightController rangeHighlightController;
    private ClickableTilesController clickableTiles;

    private HashSet<Hex> possibleMoveHexes = new HashSet<>();
    private HashSet<BattleUnit> possibleAttacks = new HashSet<>();

    private Random random = new Random();

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;

        turnController = new TurnController(battlefield.getAllUnits());
        turnController.addQueueListener(this);
        turnController.current().addStateListener(this); // first turn must be added directly
        turnController.addTurnStateListener(this);
        turnController.setTurnEndListener(this);

        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addTileInputListener(this);

        highlightController = new HighlightController(battlefield);
        clickableTiles = new ClickableTilesController(battlefield);
        inputProcessor.addTileInputListener(clickableTiles);
        rangeHighlightController = new RangeHighlightController(battlefield);
        inputProcessor.addTileInputListener(rangeHighlightController);

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
            BattleUnit unit = battlefield.getUnitAt(tile.hex);
            if (possibleMoveHexes.contains(tile.hex)) {
                new UnitMover(battlefield.transform, turnController.current().getUnit(), battlefield, tile);
                turnController.current().markHasMoved();
                turnController.current().changeState(TurnState.IDLE);
            } else if (unit != null && possibleAttacks.contains(unit)) {
                attack(turnController.current().getUnit(), unit);
                resolveDeath(unit);
                turnController.current().changeState(TurnState.ENDED);
            }
        }
    }

    /** Make one unit deal its attack damage to another unit. */
    private void attack(BattleUnit attacker, BattleUnit target) {
        int attack = attacker.getUnit().getAttack().getValue();
        int defence = target.getUnit().getDefense().getValue();

        // damage formula
        int damage = attack + random.nextInt(attack);
        int block = defence + random.nextInt(defence);
        int total = Math.max(0, damage - block);

        target.getUnit().takeDamage(total);
        System.out.println(target.getUnit().getType() + " took " + total + " damage!");
    }

    /** Resolves everything about a unit dying, if it is dead. */
    public void resolveDeath(BattleUnit unit) {
        if (unit.getUnit().isDead()) {
            battlefield.removeUnit(unit);
            turnController.remove(unit);
        }
    }

    @Override
    public void onTileDragged(Tile prev, Tile now, int button) {

    }

    @Override
    public void onTileTouchDown(Tile tile, int button) {

    }

    @Override
    public void onTileTouchUp(Tile tile, int button) {

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
        highlightController.clearAll();
        clickableTiles.clear();

        BattleUnit unit = turn.getUnit();

        Hex pos = battlefield.getPositionOf(unit);
        highlightController.setCurrentUnit(pos);

        findPossibleMoves(turn, pos);
        findPossibleAttacks(unit, pos);
    }

    /** Find possible move of possible in the this turn state. Possible moves will be stored in {@code possibleMoveHexes}
     * and the tiles will be highlighted. */
    private void findPossibleMoves(Turn turn, Hex start) {
        if (!turn.hasMoved()) {

            int movement = turn.getUnit().getUnit().getMovementSpeed().getValue();
            Function<Hex, Boolean> impassable = hex -> !battlefield.isWithin(hex) || battlefield.isOccupied(hex);
            HexPather pather = new HexPather(start, movement, impassable);

            pather.dijkstra();
            possibleMoveHexes = pather.getAllReachableHexes();
            possibleMoveHexes.remove(start);

            highlightController.setValidMoves(possibleMoveHexes);
            clickableTiles.addAll(possibleMoveHexes);
            rangeHighlightController.setRange(turn.getUnit().getUnit().getRange().getValue());
            rangeHighlightController.setHexes(possibleMoveHexes);
            rangeHighlightController.addHex(start);
        } else {
            possibleMoveHexes.clear();
            rangeHighlightController.clearHexes();
        }
    }

    /** Find possible attacks in range of unit. Possible attacks will be stored in {@code possibleAttacks}
     * and the tiles will be highlighted. */
    private void findPossibleAttacks(BattleUnit unit, Hex pos) {
        int range = Math.max(1, unit.getUnit().getRange().getValue());

        possibleAttacks.clear();
        HashSet<Hex> possibleAttackHexes = new HashSet<>();

        for (BattleUnit other : battlefield.getAllUnits()) {
            if (other != unit && other.getAllegiance() != unit.getAllegiance()) {
                Hex otherPos = battlefield.getPositionOf(other);
                if (pos.distManhattan(otherPos) <= range) {
                    possibleAttacks.add(other);
                    possibleAttackHexes.add(otherPos);
                }
            }
        }

        highlightController.setValidAttacks(possibleAttackHexes);
        clickableTiles.addAll(possibleAttackHexes);
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
