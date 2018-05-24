package com.eastvillage.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.TreeSet;

/** The LayeredDraw is a TreeSet collection of things to be drawn in layers. The layers will be defined by ZDrawables
 * {@code getZ()} method where a low z value will be drawn first. When all ZDrawables have been added to the
 * LayeredDraw, call {@code draw()}. Remeber to clear the layered draw if reused. */
public class LayeredDraw extends TreeSet<ZDrawable> {

    /** The LayeredDraw is a TreeSet collection of things to be drawn in layers. The layers will be defined by ZDrawables
     * {@code getZ()} method where a low z value will be drawn first. When all ZDrawables have been added to the
     * LayeredDraw, call {@code draw()}. Remeber to clear the layered draw if reused. */
    public LayeredDraw() {
        super((a, b) -> (a == b) ? 0 : a.getZ() - b.getZ());
    }

    /** Draws all elements. */
    public void draw(SpriteBatch batch) {
        for (ZDrawable zDrawable : this) {
            zDrawable.draw(batch);
        }
    }
}
