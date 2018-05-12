package com.eastvillage.math;

import java.util.Objects;

public class Vector2 implements Cloneable {

    public final static Vector2 RIGHT = new Vector2(1, 0);
    public final static Vector2 UP = new Vector2(0, 1);
    public final static Vector2 ZERO = new Vector2(0, 0);

    public final float x;
    public final float y;

    /** Constructs a new vector (0, 0) */
    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Constructs a vector from the given vector
     * @param v the old vector */
    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }

    public float len() {
        return (float)Math.sqrt(x * x + y * y);
    }

    /** The squared length of the vector. */
    public float len2() {
        return x * x + y * y;
    }

    /** Returns a vector, where the x is different, but y is the same. */
    public Vector2 withX(float x) {
        return new Vector2(x, y);
    }

    /** Returns a vector, where the y is different, but x is the same. */
    public Vector2 withY(float x) {
        return new Vector2(x, y);
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 add(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }

    /** Returns a vector with the same direction, but with a length of one.
     * Nothing happens if length is zero. */
    public Vector2 normalized() {
        float len = len();
        if (len != 0) {
            return scale(1f/len);
        }
        return this;
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public Vector2 scale(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 scale(float sx, float sy) {
        return new Vector2(x * sx, y * sy);
    }

    public Vector2 scale(Vector2 v) {
        return scale(v.x, v.y);
    }

    public float dist(Vector2 v) {
        return sub(v).len();
    }

    /** Returns the squared distance to v */
    public float dist2(Vector2 v) {
        return sub(v).len2();
    }

    /** Returns a vector, that is this, but no longer than {@code limit}. */
    public Vector2 limit(float limit) {
        return limit2(limit * limit);
    }

    /** Returns a vector, that is this, but which squared length is no longer than {@code limit}. */
    public Vector2 limit2(float limit2) {
        float len2 = len2();
        if (len2 > limit2) {
            return scale((float)Math.sqrt(limit2 / len2));
        }
        return this;
    }

    /** Returns a vector with the same direction, but which length is {@code len}. */
    public Vector2 setLength(float len) {
        return setLength2(len * len);
    }

    /** Returns a vector with the same direction, but which squared length is {@code len2}. */
    public Vector2 setLength2(float len2) {
        float oldLen2 = len2();
        return (oldLen2 == 0 || oldLen2 == len2) ? this : scale((float)Math.sqrt(len2 / oldLen2));
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /** @return the angle in degrees of this vector (point) relative to the x-axis. Angles are towards the positive y-axis (typically
     *         counter-clockwise) and between 0 and 360. */
    public float angle () {
        float angle = (float)Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) angle += 360;
        return angle;
    }

    /** @return the angle in radians of this vector (point) relative to the x-axis. Angles are towards the positive y-axis.
     *         (typically counter-clockwise) */
    public float angleRad () {
        return (float)Math.atan2(y, x);
    }

    /** Rotates the Vector2 counter-clockwise by the given angle.
     * @param degrees the angle in degrees */
    public Vector2 rotate (float degrees) {
        return rotateRad((float)Math.toRadians(degrees));
    }

    /** Rotates the Vector2 counter-clockwise by the given angle.
     * @param radians the angle in radians */
    public Vector2 rotateRad (float radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        return new Vector2(newX, newY);
    }

    /** Rotates the Vector2 by 90 degrees the specified amount of times, where > 0 is counter-clockwise and < 0 is clockwise. */
    public Vector2 rotate90(int dir) {
        dir = dir % 4;
        if (dir < 0) dir += 4;
        switch (dir) {
            case 1: return new Vector2(-y, x);
            case 2: return new Vector2(-x, -y);
            case 3: return new Vector2(y, -x);
            default: return this;
        }
    }

    public Vector2 lerp(Vector2 target, float alpha) {
        final float invAlpha = 1.0f - alpha;
        float nx = (x * invAlpha) + (target.x * alpha);
        float ny = (y * invAlpha) + (target.y * alpha);
        return new Vector2(nx, ny);
    }

    public boolean isUnit() {
        return isUnit(0.000000001f);
    }

    public boolean isUnit(float margin) {
        return Math.abs(len2() - 1f) < margin;
    }

    public boolean isZero () {
        return x == 0 && y == 0;
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "MethodDoesntCallSuperMethod"})
    @Override
    protected Vector2 clone() {
        return new Vector2(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean epsilonEquals(Vector2 other, float epsilon) {
        if (other == null) return false;
        if (Math.abs(other.x - x) > epsilon) return false;
        if (Math.abs(other.y - y) > epsilon) return false;
        return true;
    }

    public boolean epsilonEquals(float x, float y, float epsilon) {
        if (Math.abs(x - this.x) > epsilon) return false;
        if (Math.abs(y - this.y) > epsilon) return false;
        return true;
    }

    public boolean epsilonEquals(Vector2 other) {
        return epsilonEquals(other, 1e-16f);
    }

    public boolean epsilonEquals(float x, float y) {
        return epsilonEquals(x, y, 1e-16f);
    }
}
