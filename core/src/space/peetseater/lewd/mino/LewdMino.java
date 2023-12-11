package space.peetseater.lewd.mino;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LewdMino extends ApplicationAdapter {
	SpriteBatch batch;

	protected static final int WIDTH = 1280;
	protected static final int HEIGHT = 720;

	protected  static final int FPS = 60;

	PlayManager playManager;
	KeyboardInput keyboardInput;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playManager = new PlayManager();
		keyboardInput = new KeyboardInput();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		float timeSinceLastFrame = Gdx.graphics.getDeltaTime();
		keyboardInput.update();
		playManager.update(timeSinceLastFrame);

		batch.begin();
		playManager.render(batch, timeSinceLastFrame);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
