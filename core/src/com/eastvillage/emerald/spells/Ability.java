package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;

/** An ability of any kind, shown in a unit's spell menu. */
public interface Ability {
    BattleAbility createBattleInstance(BattleUnit owner);
    String getName();
    String getDescription();
    TextureRegion getIcon();
}
