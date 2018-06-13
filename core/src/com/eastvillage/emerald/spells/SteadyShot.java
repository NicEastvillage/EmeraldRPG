package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.battlefield.BattleUnit;

import java.util.List;

public class SteadyShot implements SingleTargetSpell {

    public SteadyShot() {

    }

    @Override
    public List<BattleUnit> filterValidTargets(List<BattleUnit> units) {
        return null;
    }

    @Override
    public void resolve(BattleUnit target) {

    }

    @Override
    public BattleAbility createBattleInstance(BattleUnit owner) {
        return null;
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
        return null;
    }
}
