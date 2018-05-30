package com.eastvillage.emerald;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.eastvillage.emerald.battle.BattleController;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.Tile;
import com.eastvillage.emerald.battle.gui.BattleStage;
import com.eastvillage.engine.GameObject;
import com.eastvillage.engine.LayeredDraw;

public class BattleScreen implements Screen {

    private OrthographicCamera camera;
    private Skin skin;
    private EmeraldGame game;
    private GameObject root;
    private BattleController controller;
    private BattleStage stage;
    private LayeredDraw layeredDraw = new LayeredDraw();

    public BattleScreen(EmeraldGame game) {
        this.game = game;

        skin = new Skin();
        skin.addRegions(EmeraldGame.getAsset(Assets.GUI_ATLAS));
        skin.load(Gdx.files.internal(Assets.GUI_STYLE));

        camera = new OrthographicCamera(GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        camera.zoom = GameInfo.ZOOM;
        camera.position.set(Tile.SPACING_WIDTH * Battlefield.TILE_HOR / 2f, Tile.SPACING_HEIGHT * Battlefield.TILE_VERT / 2f - Tile.SPACING_HEIGHT / 2f, 0);
        camera.update();

        root = new GameObject();
        Battlefield battlefield = new Battlefield(root.transform);
        controller = new BattleController(battlefield, camera);
        stage = new BattleStage(controller, skin);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(controller.getInputProcessor());
        Gdx.input.setInputProcessor(multiplexer);
    }

    public static void loadAssets(AssetManager manager) {
        manager.load(Assets.GUI_ATLAS, TextureAtlas.class);

        manager.load(Assets.GRASS, Texture.class);
        manager.load(Assets.HIGHLIGHT_MOVE, Texture.class);
        manager.load(Assets.HIGHLIGHT_ATTACK, Texture.class);
        manager.load(Assets.HIGHLIGHT_CURRENT_UNIT, Texture.class);
        manager.load(Assets.HOVER_SHADOW, Texture.class);

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

        root.registerDraws(layeredDraw);
        layeredDraw.draw(batch);

        batch.end();
        stage.act();
        stage.draw();
        layeredDraw.clear();
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
        stage.dispose();
        game.getAssetManager().clear();
    }
}
