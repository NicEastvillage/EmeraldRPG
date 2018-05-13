package com.eastvillage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.eastvillage.emerald.EmeraldGame;
import com.eastvillage.emerald.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = GameInfo.SCREEN_WIDTH;
		config.height = GameInfo.SCREEN_HEIGHT;
		config.title = "Emerald RPG";
		config.foregroundFPS = 60;

		new LwjglApplication(new EmeraldGame(), config);
	}
}
