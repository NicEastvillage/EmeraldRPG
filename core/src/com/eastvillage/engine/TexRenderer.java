package com.eastvillage.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.utility.math.Vector2;

public final class TexRenderer implements Component, ZDrawable {

    private boolean enabled = true;

    private TransformTree<GameObject> transform;
    private Vector2 scale = Vector2.ONE;
    private TextureRegion texReg;
    private boolean shouldDispose;
    private int z = 0;

    public TexRenderer(TransformTree<GameObject> transform, Texture texture, boolean shouldDispose) {
        this(transform, new TextureRegion(texture), shouldDispose);
    }

    public TexRenderer(TransformTree<GameObject> transform, TextureRegion texReg, boolean shouldDispose) {
        this(transform, texReg, Vector2.ONE, shouldDispose);
    }

    public TexRenderer(TransformTree<GameObject> transform, Texture texture, float scaleX, float scaleY, boolean shouldDispose) {
        this(transform, new TextureRegion(texture), scaleX, scaleY, shouldDispose);
    }

    public TexRenderer(TransformTree<GameObject> transform, TextureRegion texReg, float scaleX, float scaleY, boolean shouldDispose) {
        this(transform, texReg, new Vector2(scaleX, scaleY), shouldDispose);
    }

    public TexRenderer(TransformTree<GameObject> transform, Texture texture, Vector2 scale, boolean shouldDispose) {
        this(transform, new TextureRegion(texture), scale, shouldDispose);
    }

    public TexRenderer(TransformTree<GameObject> transform, TextureRegion texReg, Vector2 scale, boolean shouldDispose) {
        this.transform = transform;
        this.scale = scale;
        this.texReg = new TextureRegion(texReg);
        this.shouldDispose = shouldDispose;
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
        if (texReg != null) {
            Vector2 position = transform.getWorldPosition();
            float rotation = transform.getWorldRotation();
            float sprWidth = texReg.getRegionWidth();
            float sprHeight = texReg.getRegionHeight();
            float originX = sprWidth * 0.5f;
            float originY = sprHeight * 0.5f;
            batch.draw(texReg, position.x - originX, position.y - originY, originX, originY, sprWidth, sprHeight, scale.x, scale.y, (float)Math.toDegrees(rotation));
        }
    }

    @Override
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void dispose() {
        if (shouldDispose) {
            texReg.getTexture().dispose();
        }
    }

    @Override
    public void enable(boolean state) {
        enabled = state;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        if (scale == null) throw new IllegalArgumentException("Scale cannot be null.");
        this.scale = scale;
    }

    public TextureRegion getTextureRegion() {
        return texReg;
    }

    public void setTex(Texture texture, boolean shouldDispose) {
        setTex(new TextureRegion(texture), shouldDispose);
    }

    public void setTex(TextureRegion sprite, boolean shouldDispose) {
        if (this.texReg != null && this.shouldDispose) {
            this.texReg.getTexture().dispose();
        }
        this.texReg = sprite;
        this.shouldDispose = shouldDispose;
    }

    public void flipTex(boolean x, boolean y) {
        texReg.flip(x, y);
    }
}
