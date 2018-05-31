package com.eastvillage.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.utility.math.Vector2;

/** The TexAnimation component is used to draw sprite animation. It supports rotation, scaling, and layers. */
public class TexAnimation implements Component, ZDrawable {

    private boolean enabled = true;

    private TransformTree<GameObject> transform;
    private Vector2 scale = Vector2.ONE;
    private boolean paused = false;

    private int z = 0;

    private float time = 0;
    private Animation<TextureRegion> animation;

    public TexAnimation(TransformTree<GameObject> transform, Animation<TextureRegion> animation) {
        transform = transform;
        this.animation = animation;
    }

    public TexAnimation(TransformTree<GameObject> transform, Texture texture, int columns, int rows, float secondsPerFrame, Animation.PlayMode playMode) {
        this(transform, texture, columns, rows, secondsPerFrame, new Vector2(1, 1), playMode);
    }

    public TexAnimation(TransformTree<GameObject> transform, Texture texture, int columns, int rows, float secondsPerFrame, float scaleX, float scaleY, Animation.PlayMode playMode) {
        this(transform, texture, columns, rows, secondsPerFrame, new Vector2(scaleX, scaleY), playMode);
    }

    public TexAnimation(TransformTree<GameObject> transform, Texture texture, int columns, int rows, float secondsPerFrame, Vector2 scale, Animation.PlayMode playMode) {
        this.scale = scale;
        this.transform = transform;

        setAnimation(texture, columns, rows, secondsPerFrame, playMode);
    }

    public void setAnimation(Texture texture, int columns, int rows, float secondsPerFrame, Animation.PlayMode playMode) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<>(secondsPerFrame, frames);
        animation.setPlayMode(playMode);
    }

    /** Set animation to an already created animation. If you wan't the new animation to start from first
     * frame call {@code resetTime()}. */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    /** Reset the internal timer. This will make the animation start over. */
    public void resetTime() {
        time = 0;
    }

    /** Pause or unpause the animation. The animation will still be drawn, but it won't change frame. */
    public void pause(boolean state) {
        paused = state;
    }

    @Override
    public void update(TransformTree<GameObject> transform) {
        if (enabled && !paused) time += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void registerDraws(LayeredDraw layeredDraw) {
        layeredDraw.add(this);
    }

    @Override
    public void enable(boolean state) {
        enabled = state;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (enabled && animation != null) {
            Vector2 position = transform.getWorldPosition();
            float rotation = transform.getWorldRotation();
            TextureRegion frame = animation.getKeyFrame(time);
            batch.draw(frame, position.x, position.y, 0, 0, frame.getRegionWidth(), frame.getRegionHeight(), scale.x, scale.y, rotation);
        }
    }

    @Override
    public int getZ() {
        return z;
    }

    /** Set the z value of this texture. Textures with high z value will be drawn on top of textures with low z value. */
    public TexAnimation setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public void dispose() {

    }
}
