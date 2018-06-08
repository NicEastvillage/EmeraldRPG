package com.eastvillage.emerald.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.emerald.battle.AmountOfDamage;
import com.eastvillage.emerald.unit.stat.Stat;
import com.eastvillage.emerald.unit.type.UnitType;

import java.util.Random;

public class Unit {

    private int owner;
    private final UnitType type;
    private Random random = new Random();

    private final Stat maxHealth, defense, attack, range, movementSpeed;

    private int currentHealth;

    public Unit(int owner, UnitType type) {
        this.type = type;

        maxHealth = new Stat(type.getBaseMaxHealth()).setLowerLimit(1);
        defense = new Stat(type.getBaseDefense());
        attack = new Stat(type.getBaseAttack()).setLowerLimit(0);
        range = new Stat(type.getBaseRange()).setLowerLimit(1);
        movementSpeed = new Stat(type.getBaseMovementSpeed()).setLowerLimit(0);

        currentHealth = maxHealth.getValue();
    }

    public AmountOfDamage getStandardAttackDamage() {
        int dmg = attack.getValue();
        dmg = dmg + random.nextInt(dmg);
        return new AmountOfDamage(dmg, 0);
    }

    /** Make this unit take an amount of damage. The amount will be reduced by the unit's defense. */
    public void takeDamage(AmountOfDamage dmg) {
        int def = defense.getValue();
        dmg.basePhysical -= def + random.nextInt(def);
        int dmgTaken = dmg.getTotal();
        if (dmgTaken < 0) return;
        currentHealth -= dmgTaken;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public int getOwner() {
        return owner;
    }

    public UnitType getType() {
        return type;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public Stat getMaxHealth() {
        return maxHealth;
    }

    public Stat getDefense() {
        return defense;
    }

    public Stat getAttack() {
        return attack;
    }

    public Stat getRange() {
        return range;
    }

    public Stat getMovementSpeed() {
        return movementSpeed;
    }

    public TextureRegion getTexture() {
        return type.getTexture();
    }
}
