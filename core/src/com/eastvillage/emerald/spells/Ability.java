package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.ClickableHighlightController;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.spells.types.AbilityType;

/** An instance of an ability in a battle. */
public interface Ability {
    AbilityType getType();
    String getName();
    String getDescription();
    TextureRegion getIcon();
}