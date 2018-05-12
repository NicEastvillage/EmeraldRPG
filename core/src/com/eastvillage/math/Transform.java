package com.eastvillage.math;

public class Transform {

    private Vector2 pos = new Vector2();
    private Vector2 ori = new Vector2();
    private float rad = 0;

    public Transform () {
    }

    /** Constructs a new Transform instance with the given position and angle
     * @param position the position
     * @param radians the angle in radians */
    public Transform (Vector2 position, float radians) {
        setPosition(position);
        setRotation(radians);
    }
    /** Constructs a new Transform instance with the given position and orientation
     * @param position the position
     * @param orientation where the transform is pointing*/
    public Transform (Vector2 position, Vector2 orientation) {
        setPosition(position);
        setOrientation(orientation);
    }

    /** Transforms the given vector by this transform
     * @param v the vector */
    public Vector2 mul (Vector2 v) {
        float x = pos.x + ori.x * v.x + -ori.y * v.y;
        float y = pos.y + ori.y * v.x + ori.x * v.y;
        return new Vector2(x, y);
    }

    /** Returns this transformation applied to the {@code other} Transform */
    public Transform mul(Transform other) {
        float oriX = ori.x*other.ori.x - ori.y*other.ori.y;
        float oriY = ori.y*other.ori.x + ori.x*other.ori.y;
        float x = pos.x + ori.x*other.pos.x - ori.y*other.pos.y;
        float y = pos.y + ori.y*other.pos.x + ori.x*other.pos.y;

        return new Transform(new Vector2(x, y), new Vector2(oriX, oriY));
    }

    /** Returns a transform that is the inverse of this transformation. */
    public Transform invs() {
        float det = ori.x*ori.x + ori.y*ori.y;

        float nc = ori.x / det;
        float ns = -ori.y / det;

        float x = -nc*pos.x + ns*pos.y;
        float y = -ns*pos.x - nc*pos.y;

        return new Transform(new Vector2(x, y), new Vector2(nc, ns));
    }

    /** @return the position, modification of the vector has no effect on the Transform */
    public Vector2 getPosition () {
        return pos;
    }

    /** Sets the rotation of this transform
     * @param radians angle in radians */
    public void setRotation (float radians) {
        rad = radians;
        ori = new Vector2((float)Math.cos(radians), (float)Math.sin(radians));
    }

    public float getRotation () {
        return rad;
    }

    /** @return A Vector2 pointing to where the body is facing */
    public Vector2 getOrientation () {
        return ori;
    }

    /** Set where the body should "look at" */
    public void setOrientation (Vector2 ori) {
        if (ori.isZero()) {
            this.ori = Vector2.RIGHT;
            rad = 0;
        } else {
            this.ori = ori.normalized();
            rad = (float) Math.atan2(this.ori.x, this.ori.y);
        }
    }

    /** Sets the position of this transform
     * @param pos the position */
    public void setPosition (Vector2 pos) {
        this.pos = pos;
    }
}
