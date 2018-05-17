package com.eastvillage.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eastvillage.utility.math.Vector2;

public class Animator implements Component {

    private boolean enabled = true;

    private Vector2 scale = Vector2.ONE;
    private Texture texture;
    private boolean shouldDispose;

    private float time = 0;
    private Animation<TextureRegion> animation;

    public Animator(Texture texture, int columns, int rows, float secondsPerFrame, Animation.PlayMode playMode, boolean shouldDispose) {
        this(texture, columns, rows, secondsPerFrame, new Vector2(1, 1), playMode, shouldDispose);
    }

    public Animator(Texture texture, int columns, int rows, float secondsPerFrame, float scaleX, float scaleY, Animation.PlayMode playMode, boolean shouldDispose) {
        this(texture, columns, rows, secondsPerFrame, new Vector2(scaleX, scaleY), playMode, shouldDispose);
    }

    public Animator(Texture texture, int columns, int rows, float secondsPerFrame, Vector2 scale, Animation.PlayMode playMode, boolean shouldDispose) {
        this.scale = scale;

        setAnimation(texture, columns, rows, secondsPerFrame, playMode, shouldDispose);
    }

    public void setAnimation(Texture texture, int columns, int rows, float secondsPerFrame, Animation.PlayMode playMode, boolean shouldDispose) {
        if (this.shouldDispose) {
            this.texture.dispose();
        }

        this.texture = texture;
        this.shouldDispose = shouldDispose;

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

    public void setPlayMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    @Override
    public void update(TransformTree<GameObject> transform) {
        time += Gdx.graphics.getDeltaTime();
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
    public void draw(SpriteBatch batch, TransformTree<GameObject> transform) {
        if (enabled && animation != null) {
            Vector2 position = transform.getWorldPosition();
            float rotation = transform.getWorldRotation();
            TextureRegion frame = animation.getKeyFrame(time);
            batch.draw(frame, position.x, position.y, 0, 0, frame.getRegionWidth(), frame.getRegionHeight(), scale.x, scale.y, rotation);
        }
    }

    @Override
    public void dispose() {
        if (shouldDispose) {
            texture.dispose();
        }
    }
}
