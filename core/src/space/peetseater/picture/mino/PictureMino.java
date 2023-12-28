package space.peetseater.picture.mino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PictureMino extends Game {
	public SoundManager soundManager;
	SpriteBatch batch;
	BitmapFont font;

	protected static final int WIDTH = 1280;
	protected static final int HEIGHT = 720;

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
		changeScreenToGameScreen();
	}

	public void changeScreenToGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen(this);
		}
		setScreen(gameScreen);
	}

	public void changeScreenToTitle() {
		if (titleScreen == null) {
			// TODO: Boot up a title screen and swap to it
		}
	}

	public void changeScreenToGameOver(int finalScore) {
		if (gameOverScreen == null) {
			// TODO: Boot up a fail screen and swap to it
			gameOverScreen = new GameOverScreen(this);
		}
		gameOverScreen.setScore(finalScore);
		setScreen(gameOverScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		if (gameOverScreen != null) gameOverScreen.dispose();
		if (gameScreen != null) gameScreen.dispose();
		if (titleScreen != null) titleScreen.dispose();
	}
}
