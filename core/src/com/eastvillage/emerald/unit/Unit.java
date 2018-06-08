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

    private final Stat maxHealth, defense, magicDefense, attack, range, movementSpeed, strength, agility, magicPower;

    private int currentHealth;

    public Unit(int owner, UnitType type) {
        this.type = type;

        maxHealth = new Stat(type.getBaseMaxHealth()).setLowerLimit(1);
        defense = new Stat(type.getBaseDefense());
        magicDefense = new Stat(type.getBaseMagicDefense());
        attack = new Stat(type.getBaseAttack()).setLowerLimit(0);
        strength = new Stat(type.getBaseStrength());
        agility = new Stat(type.getBaseAgility());
        magicPower = new Stat(type.getBaseMagicPower());
        range = new Stat(type.getBaseRange()).setLowerLimit(1);
        movementSpeed = new Stat(type.getBaseMovementSpeed()).setLowerLimit(0);

        currentHealth = maxHealth.getValue();
    }

    public AmountOfDamage getStandardAttackDamage() {
        return new AmountOfDamage(attack.getValue(), 0);
    }

    /** Make this unit take an amount of damage. The amount will be reduced by the unit's defense. */
    public void takeDamage(AmountOfDamage damage) {
        applyDefence(damage);
        int damageTaken = damage.getTotal();
        if (damageTaken < 0) return;
        currentHealth -= damageTaken;
    }

    private void applyDefence(AmountOfDamage damage) {
        int def = defense.getValue();
        float physicalReduction = def / (50f + def);
        damage.physicalModifier += -physicalReduction;

        int mgcdef = defense.getValue();
        float magicalReduction = mgcdef / (50f + mgcdef);
        damage.magicalModifier += -magicalReduction;
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
