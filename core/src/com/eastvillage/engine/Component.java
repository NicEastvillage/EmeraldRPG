package com.eastvillage.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Component {

    void update(TransformTree<GameObject> transform);
    void draw(SpriteBatch batch, TransformTree<GameObject> transform);
    void enable(boolean state);
    boolean isEnabled();
    void dispose();
}
