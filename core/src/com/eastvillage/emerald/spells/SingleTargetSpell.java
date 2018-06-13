package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.List;

/** A Spell that targets exactly one unit. */
public interface SingleTargetSpell extends Ability {
    List<BattleUnit> filterValidTargets(List<BattleUnit> units);
    void resolve(BattleUnit target);
}
