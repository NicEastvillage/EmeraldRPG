package com.eastvillage.emerald;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmeraldGame extends Game {

    private static EmeraldGame _instance;
    public static EmeraldGame get() {
        if (_instance == null) throw new RuntimeException("No instance of EmeraldGame exists.");
        return _instance;
    }

	private SpriteBatch batch;
    private AssetManager assetManager;
	
	@Override
	public void create () {
	    _instance = this;
		batch = new SpriteBatch();
		assetManager = new AssetManager();

        BattleScreen.loadAssets(assetManager);
        assetManager.finishLoading();
        setScreen(new BattleScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public static <T> T getAsset(String str) {
        return get().assetManager.get(str);
    }
}
