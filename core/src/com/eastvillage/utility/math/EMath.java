package com.eastvillage.utility.math;

public class EMath {

    /** Clamps a value between a lower and a higher limit. */
    public static int clamp(int val, int low, int high) {
        if (val < low) return low;
        if (val > high) return high;
        return val;
    }

    /** Clamps a value between a lower and a higher limit. */
    public static float clamp(float val, float low, float high) {
        if (val < low) return low;
        if (val > high) return high;
        return val;
    }

    /** Clamps a value between the two limits. It doesn't matter which limit is lowest or highest. */
    public static int clamp2(int val, int limit1, int limit2) {
        int low, high;
        if (limit1 < limit2) {
            low = limit1;
            high = limit2;
        } else {
            low = limit2;
            high = limit1;
        }
        if (val < low) return low;
        if (val > high) return high;
        return val;
    }

    /** Clamps a value between the two limits. It doesn't matter which limit is lowest or highest. */
    public static float clamp2(float val, float limit1, float limit2) {
        float low, high;
        if (limit1 < limit2) {
            low = limit1;
            high = limit2;
        } else {
            low = limit2;
            high = limit1;
        }
        if (val < low) return low;
        if (val > high) return high;
        return val;
    }
}
