package com.eastvillage.emerald.battlefield;

import com.eastvillage.math.Vector2Int;

import java.util.Objects;

public class Hex {

    public final int x, y, z;

    public Hex() {
        this(0, 0);
    }

    public Hex(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = -x - y;
    }

    public float lenManhattan() {
        return (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2;
    }

    public Hex add(Hex h) {
        return new Hex(x + h.x, y + h.y);
    }

    public Hex sub(Hex h) {
        return new Hex(x - h.x, y - h.y);
    }

    public Hex scale(int scalar) {
        return new Hex(x * scalar, y * scalar);
    }

    public float distManhattan(Hex h) {
        return sub(h).lenManhattan();
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    /** Rotates the Hex by 60 degrees the specified amount of times, where > 0 is counter-clockwise and < 0 is clockwise. */
    public Hex rotate60(int dir) {
        dir = dir % 6;
        if (dir < 0) dir += 6;
        switch (dir) {
            case 1: return new Hex(-y, -z);
            case 2: return new Hex(z, x);
            case 3: return new Hex(-x, -y);
            case 4: return new Hex(y, z);
            case 5: return new Hex(-z, -x);
            default: return this;
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex that = (Hex) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
