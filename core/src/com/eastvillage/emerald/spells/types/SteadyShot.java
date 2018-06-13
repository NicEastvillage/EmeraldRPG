package com.eastvillage.emerald.spells.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.SingleTargetSpell;

import java.util.List;
import java.util.stream.Collectors;

public class SteadyShot implements SingleTargetSpellType {

    @Override
    public List<BattleUnit> filterValidTargets(BattleUnit caster, List<BattleUnit> units) {
        return units.stream().filter(caster::isEnemy).collect(Collectors.toList());
    }

    @Override
    public void resolve(BattleUnit target) {
        // TODO Deal damage
    }

    @Override
    public Ability createInstance(BattleController controller, BattleUnit owner) {
        return new SingleTargetSpell(controller, owner, this);
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
