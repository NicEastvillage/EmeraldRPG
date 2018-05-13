package com.eastvillage.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;

public class GameObject {

    public final TransformTree<GameObject> transform;

    private HashSet<Component> components;
    private boolean enabled = true;

    public GameObject() {
        transform = new TransformTree<>(this);
        components = new HashSet<>();
    }

    public final void addComponent(Component component) {
        components.add(component);
    }

    public final <T extends Component> T getComponent(Class<T> tClass) {
        for (Component comp : components) {
            if (tClass.isInstance(comp)) {
                return (T)comp;
            }
        }
        return null;
    }

    public final void enable(boolean state) {
        enabled = onEnableChange(state);
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void update() {
        onUpdate();
        for (Component component : components) {
            if (component.isEnabled()) {
                component.update(transform);
            }
        }
        for (TransformTree<GameObject> child : transform.getChildren()) {
            if (child.target.isEnabled()) {
                child.target.update();
            }
        }
    }

    public final void draw(SpriteBatch batch) {
        for (Component component : components) {
            if (component.isEnabled()) {
                component.draw(batch, transform);
            }
        }
        for (TransformTree<GameObject> child : transform.getChildren()) {
            if (child.target.isEnabled()) {
                child.target.draw(batch);
            }
        }
    }

    public final void dispose() {
        onDispose();
        for (Component component : components) {
            component.dispose();
        }
        for (TransformTree<GameObject> child : transform.getChildren()) {
            child.target.dispose();
        }
    }

    /** Subclasses are allowed to override this */
    protected void onUpdate() {

    }

    /** Subclasses are allowed to override this */
    protected void onDispose() {

    }

    /** Subclasses are allowed to override this. Should return the new state. */
    protected boolean onEnableChange(boolean newState) {
        return newState;
    }
}
