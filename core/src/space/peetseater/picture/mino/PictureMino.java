package space.peetseater.picture.mino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.peetseater.picture.mino.screens.GameOverScreen;
import space.peetseater.picture.mino.screens.GameScreen;
import space.peetseater.picture.mino.screens.TitleScreen;

public class PictureMino extends Game {
	public SoundManager soundManager;
	public SpriteBatch batch;
	public BitmapFont font;

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	GameScreen gameScreen;
	Screen titleScreen;
	GameOverScreen gameOverScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// TODO: Use the distance field free font techniques.
		font = new BitmapFont();
		font.setUseIntegerPositions(true);
		font.setColor(Color.WHITE);
		soundManager = new SoundManager();
		changeScreenToTitle();
	}

	public void changeScreenToGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen(this);
		}
		setScreen(gameScreen);
	}

	public void changeScreenToTitle() {
		if (titleScreen == null) {
			titleScreen = new TitleScreen(this);
		}
		setScreen(titleScreen);
	}

	public void changeScreenToGameOver(int finalScore) {
		if (gameOverScreen == null) {
			gameOverScreen = new GameOverScreen(this);
		}
		gameOverScreen.setScore(finalScore);
		setScreen(gameOverScreen);
	}
	
	@Override
	public void dispose () {
		font.dispose();
		if (gameOverScreen != null) gameOverScreen.dispose();
		if (gameScreen != null) gameScreen.dispose();
		if (titleScreen != null) titleScreen.dispose();
	}
}
