package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.List;

/** A Spell that targets exactly one unit. */
public interface SingleTargetSpell extends Spell {
    List<BattleUnit> filterValidTargets(List<BattleUnit> units);
    void setTarget(BattleUnit target);
}
