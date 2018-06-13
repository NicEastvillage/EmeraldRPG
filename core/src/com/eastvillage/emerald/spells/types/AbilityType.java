package com.eastvillage.emerald.spells.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.spells.Ability;

/** An ability of any kind */
public interface AbilityType {
    Ability createInstance(BattleController controller, BattleUnit owner);
    String getName();
    String getDescription();
    TextureRegion getIcon();
}
