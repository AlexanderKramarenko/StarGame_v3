package ru.alexander_kramarenko.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.alexander_kramarenko.StarGame_v3;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 400;
		config.resizable = false;
		new LwjglApplication(new StarGame_v3(), config);
	}
}
