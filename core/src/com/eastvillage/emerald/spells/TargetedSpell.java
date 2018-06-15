package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;

import java.util.Set;

/** A spell that requires the selecting of a target position, unit, or other. */
public interface TargetedSpell extends Spell {

    Set<Hex> findTargets(BattleUnit caster, Battlefield battlefield);
}
