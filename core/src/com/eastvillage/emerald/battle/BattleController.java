package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.unit.Unit;

public class BattleController {

    private Battlefield battlefield;
    private TurnController turnController;

    public BattleController(Battlefield battlefield) {
        this.battlefield = battlefield;
        turnController = new TurnController(battlefield.getAllUnits());
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public TurnController getTurnController() {
        return turnController;
    }
}
