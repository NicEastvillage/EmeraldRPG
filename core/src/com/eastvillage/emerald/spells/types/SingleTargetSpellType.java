package com.eastvillage.emerald.spells.types;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.List;

/** A Spell that targets exactly one unit. */
public interface SingleTargetSpellType extends AbilityType {

    List<BattleUnit> filterValidTargets(BattleUnit caster, List<BattleUnit> units);
    void resolve(BattleUnit target, Tile tile);
}
