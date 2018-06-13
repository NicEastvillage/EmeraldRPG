package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Hex;

/** An instance of an ability in a battle. */
public interface BattleAbility {
    void onClick();
    void setTarget(BattleUnit unit, Hex hex);
}