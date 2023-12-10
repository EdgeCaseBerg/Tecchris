package space.peetseater.lewd.mino;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LewdMino extends ApplicationAdapter {
	SpriteBatch batch;

	protected static final int WIDTH = 1280;
	protected static final int HEIGHT = 720;

	protected  static final int FPS = 60;

	PlayManager playManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playManager = new PlayManager();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		float timeSinceLastFrame = Gdx.graphics.getDeltaTime();
		playManager.update(timeSinceLastFrame);

		batch.begin();
		playManager.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
