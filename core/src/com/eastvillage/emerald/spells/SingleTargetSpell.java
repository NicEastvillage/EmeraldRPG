package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.spells.types.SingleTargetSpellType;

import java.util.Set;

public class SingleTargetSpell implements TargetedSpell {

    private BattleUnit owner;
    private SingleTargetSpellType type;

    public SingleTargetSpell(BattleUnit owner, SingleTargetSpellType type) {
        this.owner = owner;
        this.type = type;
    }

    @Override
    public Set<Hex> findTargets(BattleUnit caster, Battlefield battlefield) {
        return type.findTargets(caster, battlefield);
    }

    @Override
    public void resolve(BattleUnit target, Tile tile) {
        type.resolve(target, tile);
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
