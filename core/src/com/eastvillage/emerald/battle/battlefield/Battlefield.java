package com.eastvillage.emerald.battle.battlefield;

import com.eastvillage.emerald.battle.Allegiance;
import com.eastvillage.emerald.unit.type.Knight;
import com.eastvillage.emerald.unit.type.Priest;
import com.eastvillage.emerald.unit.type.Ranger;
import com.eastvillage.emerald.unit.Unit;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TransformTree;
import com.eastvillage.utility.collections.BiMap;
import com.eastvillage.utility.math.Vector2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/** A battlefield is a game object and responsible for displaying and positioning the tiles and units of the battle. */
public class Battlefield extends GameObject implements Iterable<Tile> {

    public static final int TILE_VERT = 9;
    public static final int TILE_HOR = 12;

    private HashMap<Hex, Tile> map = new HashMap<>();
    private BiMap<BattleUnit, Hex> unitMap = new BiMap<>();

    public Battlefield() {
        this(null);
    }

    public Battlefield(TransformTree<GameObject> parent) {
        super(parent);

        createTiles();


        placeUnit(new Hex(4, 4), new BattleUnit(new Unit(0, new Knight()), Allegiance.LEFT));
        placeUnit(new Hex(7, 3), new BattleUnit(new Unit(0, new Priest()), Allegiance.RIGHT));
        placeUnit(new Hex(6, 6), new BattleUnit(new Unit(0, new Ranger()), Allegiance.RIGHT));
    }

    /** Create and setup tiles. */
    private void createTiles() {
        for (int y = 0; y < TILE_VERT; y++) {
            int tiles_hor = TILE_HOR + ((y & 1) == 0 ? 1 : 0);
            for (int x = 0; x < tiles_hor; x++) {
                int q = x - y / 2;
                int r = y;
                Hex hex = new Hex(q, r);
                Tile tile = new Tile(hex,transform);
                map.put(hex, tile);
            }
        }
    }

    public Tile getTile(Hex hex) {
        return map.get(hex);
    }

    /** Returns true if the given hex is within the battlefield. */
    public boolean isWithin(Hex hex) {
        return map.containsKey(hex);
    }

    /** Returns true if a BattleUnit occupies the given hex. */
    public boolean isOccupied(Hex hex) {
        if (!isWithin(hex)) throw new NotWithinBattlefieldException(hex, "Hex " + hex.toString() + " is not within the battlefield.");
        return unitMap.inv().containsKey(hex);
    }

    /** Place a unit on a hex and set it's parent's transform to the associated tile. A unit can only be on one
     * hex at the time, so if already placed, it will be moved. If unit is null, the unit on the hex (if any)
     * will be removed and returned. Will throw an exception, if hex is not within battlefield.
     * @return the unit previously placed on the hex. */
    public BattleUnit placeUnit(Hex hex, BattleUnit unit) {
        if (!isWithin(hex)) throw new NotWithinBattlefieldException(hex, "Hex " + hex.toString() + " is not within the battlefield.");

        BattleUnit prev = unitMap.inv().get(hex);

        unitMap.put(unit, hex);

        Tile tile = map.get(hex);
        unit.transform.setParent(tile.transform);

        return prev;
    }

    /** Removes a unit from the battlefield. */
    public boolean removeUnit(BattleUnit unit) {
        Hex pos = getPositionOf(unit);
        if (pos == null) return false;
        unitMap.remove(unit);
        unit.transform.setParent(null);
        return true;
    }

    /** Returns the hex, where the given unit is placed. Returns null if the unit is not placed on this battlefield. */
    public Hex getPositionOf(BattleUnit unit) {
        return unitMap.get(unit);
    }

    /** Returns the unit at the given hex. Returns null if there is no unit. */
    public BattleUnit getUnitAt(Hex hex) {
        if (!isWithin(hex)) throw new NotWithinBattlefieldException(hex, "Hex " + hex.toString() + " is not within the battlefield.");
        return unitMap.inv().get(hex);
    }

    /** Returns a Set of units that is on the map. */
    public Set<BattleUnit> getAllUnits() {
        return unitMap.keySet();
    }

    /** Returns a bi-directional map of BattleUnits and Tiles. */
    public BiMap<BattleUnit, Hex> getUnitMap() {
        return new BiMap<>(unitMap);
    }

    /** Convert a vector to a hex on the battlefield. The vector must be in world space. */
    public Hex pointToHex(float pointX, float pointY) {
        // We assume the battlefield has no rotation
        Vector2 fieldPos = transform.getWorldPosition();
        float x = pointX - fieldPos.x;
        float y = pointY - fieldPos.y;

        float q = 1f/Tile.SPACING_WIDTH * x  -  1f/(2*Tile.SPACING_HEIGHT) * y;
        float r = 0                          +  1f/Tile.SPACING_HEIGHT * y;

        return Hex.fromRounding(q, r);
    }

    @Override
    public Iterator<Tile> iterator() {
        return map.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super Tile> action) {
        map.values().forEach(action);
    }

    @Override
    public Spliterator<Tile> spliterator() {
        return map.values().spliterator();
    }
}
