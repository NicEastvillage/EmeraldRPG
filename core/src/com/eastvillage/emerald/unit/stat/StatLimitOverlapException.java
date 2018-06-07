package com.eastvillage.emerald.unit.stat;

/** Thrown when a Stats limit is set to be on the wrong side of the other limit creating an undefined interval. */
public class StatLimitOverlapException extends RuntimeException {

    private int lower, upper;

    public StatLimitOverlapException(int lower, int upper) {
        super("Stat limits overlap; lower limit: " + lower + ", upper limit: " + upper);
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }
}
