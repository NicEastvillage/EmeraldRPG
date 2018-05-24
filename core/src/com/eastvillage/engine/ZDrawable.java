package com.eastvillage.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ZDrawable {

    void draw(SpriteBatch batch);
    int getZ();
}
