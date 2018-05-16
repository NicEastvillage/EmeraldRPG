package com.eastvillage.emerald;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eastvillage.emerald.battlefield.Battlefield;
import com.eastvillage.emerald.battlefield.Tile;
import com.eastvillage.engine.GameObject;

public class BattleScreen implements Screen {

    private EmeraldGame game;
    private OrthographicCamera camera;
    private GameObject root;

    public BattleScreen(EmeraldGame game) {
        this.game = game;

        camera = new OrthographicCamera(GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        camera.zoom = GameInfo.ZOOM;
        camera.position.set(Tile.SPACING_WIDTH * Battlefield.TILE_HOR / 2f, Tile.SPACING_HEIGHT * Battlefield.TILE_VERT / 2f - Tile.SPACING_HEIGHT / 2f, 0);
        camera.update();

        root = new GameObject();
        new Battlefield(root.transform);
    }

    public static void loadAssets(AssetManager manager) {
        manager.load(Assets.GRASS, Texture.class);

        manager.load(Assets.KNIGHT, Texture.class);
        manager.load(Assets.RANGER, Texture.class);
        manager.load(Assets.PRIEST, Texture.class);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        root.update();

        SpriteBatch batch = game.getBatch();
        Gdx.gl.glClearColor(0xBA/255f, 0xC3/255f, 0xA1/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

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
        game.getAssetManager().clear();
    }
}
