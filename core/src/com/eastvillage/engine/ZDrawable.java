package com.eastvillage.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ZDrawable {

    void draw(SpriteBatch batch);

    /** Get the z value of this ZDrawable. Drawables with high z value will be drawn on top of Drawables with low z value. */
    int getZ();
}
