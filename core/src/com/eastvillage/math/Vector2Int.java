package com.eastvillage.math;

import java.util.Objects;

public class Vector2Int {

    public final static Vector2Int RIGHT = new Vector2Int(1, 0);
    public final static Vector2Int UP = new Vector2Int(0, 1);
    public final static Vector2Int ZERO = new Vector2Int(0, 0);

    public final int x;
    public final int y;

    /** Constructs a new vector (0, 0) */
    public Vector2Int() {
        this(0, 0);
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Constructs a vector from the given vector
     * @param v the old vector */
    public Vector2Int(Vector2Int v) {
        this(v.x, v.y);
    }

    public float lenManhattan() {
        return Math.abs(x) + Math.abs(y);
    }

    /** Returns a vector, where the x is different, but y is the same. */
    public Vector2Int withX(int x) {
        return new Vector2Int(x, y);
    }

    /** Returns a vector, where the y is different, but x is the same. */
    public Vector2Int withY(int x) {
        return new Vector2Int(x, y);
    }

    public Vector2Int add(Vector2Int v) {
        return new Vector2Int(x + v.x, y + v.y);
    }

    public Vector2Int sub(Vector2Int v) {
        return new Vector2Int(x - v.x, y - v.y);
    }

    public Vector2Int add(int x, int y) {
        return new Vector2Int(this.x + x, this.y + y);
    }

    public float dot(Vector2Int v) {
        return x * v.x + y * v.y;
    }

    public Vector2Int scale(int scalar) {
        return new Vector2Int(x * scalar, y * scalar);
    }

    public Vector2Int scale(int sx, int sy) {
        return new Vector2Int(x * sx, y * sy);
    }

    public Vector2Int scale(Vector2Int v) {
        return scale(v.x, v.y);
    }

    public float distManhattan(Vector2Int v) {
        return sub(v).lenManhattan();
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /** Rotates the Vector2Int by 90 degrees the specified amount of times, where > 0 is counter-clockwise and < 0 is clockwise. */
    public Vector2Int rotate90(int dir) {
        dir = dir % 4;
        if (dir < 0) dir += 4;
        switch (dir) {
            case 1: return new Vector2Int(-y, x);
            case 2: return new Vector2Int(-x, -y);
            case 3: return new Vector2Int(y, -x);
            default: return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2Int that = (Vector2Int) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
