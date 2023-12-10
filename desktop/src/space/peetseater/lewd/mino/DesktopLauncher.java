package space.peetseater.lewd.mino;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import space.peetseater.lewd.mino.LewdMino;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1280,720);
		config.setResizable(false);
		config.setTitle("Lewdomino");
		new Lwjgl3Application(new LewdMino(), config);
	}
}