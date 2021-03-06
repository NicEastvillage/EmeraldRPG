package com.eastvillage.emerald.unit.stat;

import com.eastvillage.utility.math.EMath;

public class Stat {

    private int finalValue;
    private int baseValue;
    private int positiveFlatModifiers = 0;
    private float positivePercentageModifiers = 0;
    private int negativeFlatModifiers = 0;            // stored as negative number
    private float negativePercentageModifiers = 0;     // stored as negative number

    private int upperLimit = Integer.MAX_VALUE;
    private int lowerLimit = Integer.MIN_VALUE;

    public Stat(int baseValue) {
        this.baseValue = baseValue;
        recalcFinalValue();
    }

    private int recalcFinalValue() {
        // positives are added first, then negatives
        finalValue = (int) ((baseValue + positiveFlatModifiers) * (1 + positivePercentageModifiers + negativePercentageModifiers) + negativeFlatModifiers);
        finalValue = EMath.clamp(finalValue, lowerLimit, upperLimit);
        return finalValue;
    }

    public int getValue() {
        return finalValue;
    }

    /** Add a flat modifier for this Stat. The value can be both negative or positive. Note, that modifiers should
     * not be removed using this method. See {@link #removeFlatModifier(int)}. */
    public int addFlatModifier(int val) {
        if (val >= 0) {
            positiveFlatModifiers += val;
        } else {
            negativeFlatModifiers -= val;
        }
        return recalcFinalValue();
    }

    /** Remove a flat modifier for this Stat. The value must be the same as when originally added, wherefore a modifier of
     * 5 must removed as 5, and a modifier of -8 must be removed as -8. Removing a modifier, that were never added
     * will create errors. */
    public int removeFlatModifier(int val) {
        if (val >= 0) {
            positiveFlatModifiers -= val;
        } else {
            negativeFlatModifiers += val;
        }
        return recalcFinalValue();
    }

    /** Add a percentage modifier for this Stat, where 0.1 = 10% bonus. The value can be both negative or positive.
     * Note, that modifiers should not be removed using this method. See {@link #removePercentageModifier(float)}. */
    public int addPercentageModifier(float val) {
        if (val >= 0) {
            positivePercentageModifiers += val;
        } else {
            negativePercentageModifiers -= val;
        }
        return recalcFinalValue();
    }

    /** Remove a percentage modifier for this Stat, where 0.1 = 10% bonus. The value must be the same as when
     * originally added, wherefore a modifier of 0.2 must removed as 0.2, and a modifier of -0.1 must be removed
     * as -0.1. Removing a modifier, that were never added will create errors. */
    public int removePercentageModifier(float val) {
        if (val >= 0) {
            positivePercentageModifiers -= val;
        } else {
            negativePercentageModifiers += val;
        }
        return recalcFinalValue();
    }

    /** Restrict this Stat between two limits. Default limits are INT_MIN and INT_MAX. */
    public Stat setLimits(int lower, int upper) {
        if (lower > upper) throw new StatLimitOverlapException(lower, upper);
        lowerLimit = lower;
        upperLimit = upper;
        recalcFinalValue();
        return this;
    }

    /** Restrict this Stat between lower than a certain limit. Default upper limit INT_MAX. */
    public Stat setUpperLimit(int limit) {
        upperLimit = limit;
        recalcFinalValue();
        return this;
    }

    /** Restrict this Stat between greater than a certain limit. Default lower limit INT_MIN. */
    public Stat setLowerLimit(int limit) {
        lowerLimit = limit;
        recalcFinalValue();
        return this;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }
}
