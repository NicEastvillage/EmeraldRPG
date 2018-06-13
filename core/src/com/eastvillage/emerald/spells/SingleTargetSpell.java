package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.spells.types.SingleTargetSpellType;

public class SingleTargetSpell implements Ability {

    private BattleController controller;
    private BattleUnit owner;
    private SingleTargetSpellType type;

    public SingleTargetSpell(BattleController controller, BattleUnit owner, SingleTargetSpellType type) {
        this.controller = controller;
        this.owner = owner;
        this.type = type;
    }

    @Override
    public void onClick() {
        // TODO Enter/leave turnState.TARGETING + prompt for target
    }

    @Override
    public void setTarget(BattleUnit unit, Hex hex) {
        // TODO Cast spell?
        // TODO End turn?
    }
}
