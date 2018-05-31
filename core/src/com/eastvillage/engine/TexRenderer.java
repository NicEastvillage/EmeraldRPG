package com.eastvillage.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.utility.math.Vector2;

/** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
public final class TexRenderer implements Component, ZDrawable {

    private boolean enabled = true;

    private TransformTree<GameObject> transform;
    private Vector2 scale = Vector2.ONE;
    private TextureRegion tex;
    private int z = 0;

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, Texture texture) {
        this(transform, texture == null ? null : new TextureRegion(texture));
    }

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, TextureRegion tex) {
        this(transform, tex, Vector2.ONE);
    }

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, Texture texture, float scaleX, float scaleY) {
        this(transform, texture == null ? null : new TextureRegion(texture), scaleX, scaleY);
    }

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, TextureRegion tex, float scaleX, float scaleY) {
        this(transform, tex, new Vector2(scaleX, scaleY));
    }

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, Texture texture, Vector2 scale) {
        this(transform, new TextureRegion(texture), scale);
    }

    /** The TexRenderer component is used to draw textures or texture regions. It supports rotation, scaling and layers. */
    public TexRenderer(TransformTree<GameObject> transform, TextureRegion tex, Vector2 scale) {
        this.transform = transform;
        this.scale = scale;
        this.tex = tex;
    }

    @Override
    public void update(TransformTree<GameObject> transform) {

    }

    @Override
    public void registerDraws(LayeredDraw layeredDraw) {
        layeredDraw.add(this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (tex != null) {
            Vector2 position = transform.getWorldPosition();
            float rotation = transform.getWorldRotation();
            float sprWidth = tex.getRegionWidth();
            float sprHeight = tex.getRegionHeight();
            float originX = sprWidth * 0.5f;
            float originY = sprHeight * 0.5f;
            batch.draw(tex, position.x - originX, position.y - originY, originX, originY, sprWidth, sprHeight, scale.x, scale.y, (float)Math.toDegrees(rotation));
        }
    }

    @Override
    public int getZ() {
        return z;
    }

    /** Set the z value of this texture. Textures with high z value will be drawn on top of textures with low z value. */
    public TexRenderer setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void enable(boolean state) {
        enabled = state;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /** Returns the scaling of the drawn texture. */
    public Vector2 getScale() {
        return scale;
    }

    /** Scale the drawn texture. */
    public void setScale(Vector2 scale) {
        if (scale == null) throw new IllegalArgumentException("Scale cannot be null.");
        this.scale = scale;
    }

    /** Returns the texture region */
    public TextureRegion getTex() {
        return tex;
    }

    /** Change the drawn texture. */
    public void setTex(Texture texture) {
        setTex(new TextureRegion(texture));
    }

    /** Change the drawn texture region. */
    public void setTex(TextureRegion tex) {
        this.tex = tex;
    }

    /** Flip the texture horizontally and/or vertically. */
    public void flipTex(boolean horizontally, boolean vertically) {
        tex.flip(horizontally, vertically);
    }
}
