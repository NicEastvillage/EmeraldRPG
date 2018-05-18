package com.eastvillage.engine;

import com.eastvillage.utility.math.Vector2;

import java.util.HashSet;
import java.util.Set;

public final class TransformTree<T> {

    public T target;
    private Transform transform;
    private TransformTree<T> parent;
    private HashSet<TransformTree<T>> children = new HashSet<>();

    public TransformTree(T target) {
        this(target, Vector2.ZERO, 0, null);
    }

    public TransformTree(T target, TransformTree<T> parent) {
        this(target, Vector2.ZERO, 0, parent);
    }

    public TransformTree(T target, Vector2 position, TransformTree<T> parent) {
        this(target, position, 0, parent);
    }

    public TransformTree(T target, Vector2 position, float radians, TransformTree<T> parent) {
        this.target = target;
        this.parent = parent;
        if (parent != null)
            parent.children.add(this);

        transform = new Transform();
        setWorldPosition(position);
        setWorldRotation(radians);
    }

    public TransformTree(T target, Vector2 position, Vector2 orientation, TransformTree<T> parent) {
        this.target = target;
        this.parent = parent;
        if (parent != null)
            parent.children.add(this);

        transform = new Transform();
        setWorldPosition(position);
        setWorldOrientation(orientation);
    }

    public void setWorld(Transform transform) {
        Transform relativeToParent = parent.getWorldTransform().invs().mul(transform);
        setLocal(relativeToParent);
    }

    private void setLocal(Transform transform) {
        this.transform.set(transform);
    }

    public Transform getWorldTransform() {
        if (parent == null) return transform;
        else return parent.getWorldTransform().mul(transform);
    }

    public Transform getLocalTransform() {
        return transform;
    }

    public Vector2 getWorldPosition() {
        if (parent == null) return transform.getPosition();
        else return getWorldTransform().getPosition();
    }

    public Vector2 getLocalPosition() {
        return transform.getPosition();
    }

    public void setWorldPosition(Vector2 position) {
        if (parent == null) transform.setPosition(position);
        else {
            Transform posAsTrans = new Transform(position, 0);
            Transform relativeToParent = parent.getWorldTransform().invs().mul(posAsTrans);
            transform.setPosition(relativeToParent.getPosition());
        }
    }

    public void setLocalPosition(Vector2 position) {
        transform.setPosition(position);
    }

    public float getWorldRotation() {
        if (parent == null) return transform.getRotation();
        else return parent.getWorldRotation() + transform.getRotation();
    }

    public float getLocalRotation() {
        return transform.getRotation();
    }

    public void setWorldRotation(float radians) {
        if (parent == null) transform.setRotation(radians);
        else transform.setRotation(radians - parent.getWorldRotation());
    }

    public void setLocalRotation(float radians) {
        transform.setRotation(radians);
    }

    public Vector2 getWorldOrientation() {
        if (parent == null) return transform.getOrientation();
        else return transform.getOrientation().rotateRad(parent.transform.getRotation());
    }

    public Vector2 getLocalOrientation() {
        return transform.getOrientation();
    }

    public void setWorldOrientation(Vector2 orientation) {
        if (parent == null) transform.setOrientation(orientation.normalized());
        else transform.setOrientation(orientation.rotateDeg(-parent.getWorldOrientation().angleRad()));
    }

    public void setLocalOrientation(Vector2 orientation) {
        transform.setOrientation(orientation);
    }

    public TransformTree<T> getParent() {
        return parent;
    }

    /** Set transform's parent. Local position/rotation stays. */
    public void setParent(TransformTree<T> newParent) {
        setParent(newParent, false);
    }

    public void setParent(TransformTree<T> newParent, boolean keepWorldPosition) {
        Vector2 prevWorldPos = getWorldPosition();
        float prevWorldRotation = getWorldRotation();

        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = newParent;

        if (newParent != null) {
            newParent.children.add(this);
        }

        if (keepWorldPosition) {
            setWorldPosition(prevWorldPos);
            setWorldRotation(prevWorldRotation);
        }
    }

    public Set<TransformTree<T>> getChildren() {
        return new HashSet<>(children);
    }
}
