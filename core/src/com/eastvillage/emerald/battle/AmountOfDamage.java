package com.eastvillage.emerald.battle;

/** Represents an amount of damage, a combination of physical and magical. The base value can be changed through
 * bonus modifiers. */
public class AmountOfDamage {

    /** The base amount of physical damage dealt. */
    public int basePhysical;
    /** The base amount of magical damage dealt. */
    public int baseMagical;

    /** A percentage modifier for the base physical amount. 0.1 = 10% increase, -0.2 = 20% decrease. */
    public float physicalModifier;
    /** A percentage modifier for the base magical amount. 0.1 = 10% increase, -0.2 = 20% decrease. */
    public float magicalModifier;
    /** A percentage modifier for both the physical and magical amount. 0.1 = 10% increase, -0.2 = 20% decrease. */
    public float universalModifier;

    /** Represents an amount of damage, a combination of physical and magical. The base value can be changed through
     * bonus modifiers. */
    public AmountOfDamage(int physical, int magical) {
        this.basePhysical = physical;
        this.baseMagical = magical;
    }

    /** Calculates and returns the final amount of physical damage. */
    public int getPhysical() {
        int finalPhysical = (int) (basePhysical * (1 + physicalModifier + universalModifier));
        if (finalPhysical < 0) finalPhysical = 0;
        return finalPhysical;
    }

    /** Calculates and returns the final amount of magical damage. */
    public int getMagical() {
        int finalMagical = (int) (baseMagical *(1 + magicalModifier + universalModifier));
        if (finalMagical < 0) finalMagical = 0;
        return finalMagical;
    }
}
