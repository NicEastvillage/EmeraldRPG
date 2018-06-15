package com.eastvillage.emerald.spells.types;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

import java.util.List;
import java.util.Set;

/** A Spell that targets exactly one unit. */
public interface SingleTargetSpellType extends AbilityType {

    Set<Hex> findTargets(BattleUnit caster, Battlefield battlefield);
    void resolve(BattleUnit target, Tile tile);
}
