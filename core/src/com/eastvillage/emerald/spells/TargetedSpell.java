package com.eastvillage.emerald.spells;

import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.ClickableHighlightController;

/** A spell that requires the selecting of a target position, unit, or other. */
public interface TargetedSpell extends Spell {

    void onTargetSelectBegin(Battlefield battlefield, ClickableHighlightController clickRequester);
    void onTargetSelectCancel(ClickableHighlightController clickRequester);
}
