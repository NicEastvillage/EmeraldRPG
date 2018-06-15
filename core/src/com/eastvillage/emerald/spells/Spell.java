package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Tile;

/** Spells are castable abilities. */
public interface Spell extends Ability {

    void resolve(BattleUnit target, Tile tile);
}
