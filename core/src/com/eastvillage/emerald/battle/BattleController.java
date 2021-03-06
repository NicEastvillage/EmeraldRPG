package com.eastvillage.emerald.battle;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.battle.gui.CastSpellButtonListener;
import com.eastvillage.emerald.battle.gui.EndTurnButtonListener;
import com.eastvillage.emerald.battle.turn.*;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

public class BattleController implements BattlefieldTileInputListener, TurnQueueListener, TurnStateListener, TurnEndListener, EndTurnButtonListener, CastSpellButtonListener {

    private Battlefield battlefield;
    private TurnController turnController;
    private BattlefieldInputProcessor inputProcessor;
    private HighlightController highlightController;
    private RangeHighlightController rangeHighlightController;
    private ClickableHighlightController clickableController;

    private HashSet<Hex> possibleMoveHexes = new HashSet<>();
    private HashSet<BattleUnit> possibleAttacks = new HashSet<>();

    private Random random = new Random();

    public BattleController(Battlefield battlefield, OrthographicCamera camera) {
        this.battlefield = battlefield;

        turnController = new TurnController(battlefield.getAllUnits());
        turnController.addQueueListener(this);
        turnController.addTurnStateListener(this);
        turnController.setTurnEndListener(this);

        inputProcessor = new BattlefieldInputProcessor(battlefield, camera);
        inputProcessor.addTileInputListener(this);

        highlightController = new HighlightController(battlefield);
        clickableController = new ClickableHighlightController(battlefield);
        inputProcessor.addTileInputListener(clickableController);
        rangeHighlightController = new RangeHighlightController(battlefield);
        inputProcessor.addTileInputListener(rangeHighlightController);
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

    /** Called when a click requested for movement happens. */
    private boolean tryMoveClickCallback(Tile tile, int button) {
        if (button == 0) {
            if (possibleMoveHexes.contains(tile.hex)) {
                new UnitMover(battlefield.transform, turnController.current().getUnit(), battlefield, tile);
                turnController.current().markHasMoved();
                turnController.current().changeState(TurnState.IDLE);
                return true;
            }
        }
        return false;
    }

    /** Called when a click requested for attacks happens. */
    private boolean tryAttackClickCallback(Tile tile, int button) {
        if (button == 0) {
            BattleUnit unit = battlefield.getUnitAt(tile.hex);
            if (unit != null && possibleAttacks.contains(unit)) {
                attack(turnController.current().getUnit(), unit);
                resolveDeath(unit);
                turnController.current().changeState(TurnState.ENDED);
            }
        }
        return false;
    }

    /** Make one unit make a standard attack against another unit. */
    private void attack(BattleUnit attacker, BattleUnit target) {
        AmountOfDamage amount = attacker.getUnit().getStandardAttackDamage();
        target.getUnit().takeDamage(amount);
        System.out.println(target.getUnit().getType().getTypeName() + " took " + amount.getTotal() + " damage!");
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
        clearPossibleMoves();
        clearPossibleAttacks();
        highlightController.clearAll();
        clickableController.clear();
    }

    @Override
    public void onTurnStateIdle(Turn turn) {
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

            clickableController.request(possibleMoveHexes, HighlightType.VALID_MOVE, this::tryMoveClickCallback);
            rangeHighlightController.setRange(turn.getUnit().getUnit().getRange().getValue());
            rangeHighlightController.setHexes(possibleMoveHexes);
            rangeHighlightController.addHex(start);
        } else {
            clearPossibleMoves();
        }
    }

    /** clickableController are not cleared as a result of calling this. */
    private void clearPossibleMoves() {
        possibleMoveHexes.clear();
        rangeHighlightController.clearHexes();
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
        
        if (possibleAttackHexes.size() != 0) {
            clickableController.request(possibleAttackHexes, HighlightType.VALID_ATTACK, this::tryAttackClickCallback);
        }
    }

    /** clickableController are not cleared as a result of calling this. */
    private void clearPossibleAttacks() {
        highlightController.clearValidAttacks();
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

    @Override
    public void onEndTurnButtonPressed() {
        turnController.current().changeState(TurnState.ENDED);
    }

    @Override
    public void onCastSpellButtonStartSelecting() {
        turnController.current().changeState(TurnState.SELECTING_SPECIAL);
    }

    @Override
    public void onCastSpellButtonCancelSelecting() {
        turnController.current().changeState(TurnState.IDLE);
    }

    @Override
    public void onCastSpellButtonCancelTargeting() {
        turnController.current().changeState(TurnState.SELECTING_SPECIAL);
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
