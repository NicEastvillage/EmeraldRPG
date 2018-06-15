package com.eastvillage.emerald.spells.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.SingleTargetSpell;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SteadyShot implements SingleTargetSpellType {

    @Override
    public Set<Hex> findTargets(BattleUnit caster, Battlefield battlefield) {
        return battlefield.getAllUnits().stream().filter(caster::isEnemy).map(battlefield::getPositionOf).collect(Collectors.toSet());
    }

    @Override
    public void resolve(BattleUnit target, Tile tile) {
        // TODO Deal damage
    }

    @Override
    public Ability createInstance(BattleUnit owner) {
        return new SingleTargetSpell(owner, this);
    }

    @Override
    public String getName() {
        return "Steady Shot";
    }

    @Override
    public String getDescription() {
        return "Deal damage to an enemy.";
    }

    @Override
    public TextureRegion getIcon() {
        return null; //TODO Create/Add icon
    }
}
