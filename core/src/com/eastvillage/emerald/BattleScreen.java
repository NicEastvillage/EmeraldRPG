package com.eastvillage.emerald;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.TexRenderer;
import com.eastvillage.math.Vector2;

public class BattleScreen implements Screen {

    private EmeraldGame game;
    private OrthographicCamera camera;
    private GameObject root;

    public BattleScreen(EmeraldGame game) {
        this.game = game;

        root = new GameObject();
        new GameObject(new Vector2(16, 16), 0.4f, root.transform).addComponent(new TexRenderer(new Texture("test.png"), true));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        root.update();

        SpriteBatch batch = game.getBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        root.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        root.dispose();
    }
}
