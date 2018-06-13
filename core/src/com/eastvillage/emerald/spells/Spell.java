package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Hex;

import java.util.List;

/** A Castable ability. */
public interface Spell extends Ability {
    void resolve();
}
