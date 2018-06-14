package com.eastvillage.emerald.battle.battlefield;

import com.eastvillage.emerald.Layers;
import com.eastvillage.emerald.battle.Allegiance;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.types.AbilityType;
import com.eastvillage.emerald.unit.Unit;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;

import java.util.ArrayList;
import java.util.List;

public class BattleUnit extends GameObject {

    private Unit unit;
    private Allegiance allegiance;
    private List<Ability> abilities;

    private TexRenderer texRenderer;

    public BattleUnit(Unit unit, Allegiance allegiance) {
        this.unit = unit;
        this.allegiance = allegiance;

        texRenderer = new TexRenderer(transform, unit.getTexture());
        texRenderer.flipTex(allegiance.isSpriteFlipped(), false);
        texRenderer.setZ(Layers.UNITS);
        addComponent(texRenderer);

        // By creating the instances of abilities here, the abilities will be per battle
        // (as opposed to owned by the unit instance across battles)
        abilities = new ArrayList<>();
        for (AbilityType abilityType : unit.getAbilities()) {
            abilities.add(abilityType.createInstance(this));
        }
    }

    public Unit getUnit() {
        return unit;
    }

    public Allegiance getAllegiance() {
        return allegiance;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public boolean isAlly(BattleUnit unit) {
        return allegiance == unit.getAllegiance();
    }

    public boolean isEnemy(BattleUnit unit) {
        return allegiance != unit.getAllegiance();
    }
}