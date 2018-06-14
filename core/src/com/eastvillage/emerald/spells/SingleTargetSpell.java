package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.spells.types.SingleTargetSpellType;

public class SingleTargetSpell implements TargetedSpell {

    private BattleUnit owner;
    private SingleTargetSpellType type;

    public SingleTargetSpell(BattleUnit owner, SingleTargetSpellType type) {
        this.owner = owner;
        this.type = type;
    }

    @Override
    public void onTargetSelectBegin(Battlefield battlefield, ClickableHighlightController clickRequester) {

    }

    @Override
    public void onTargetSelectCancel(ClickableHighlightController clickRequester) {

    }

    @Override
    public void resolve() {

    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getDescription() {
        return type.getDescription();
    }

    @Override
    public TextureRegion getIcon() {
        return type.getIcon();
    }

    @Override
    public AbilityType getType() {
        return type;
    }
}
