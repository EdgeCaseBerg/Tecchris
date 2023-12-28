package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;
import space.peetseater.picture.mino.PictureMino;

public class TitleScreen extends ScreenAdapter {
    PictureMino pictureMino;
    public TitleScreen(PictureMino pictureMino) {
        this.pictureMino = pictureMino;
    }

    // TODO: Move this to its own file I suppose?
    public class TitleScreenInputAdapter extends InputAdapter {
        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.ENTER:
                    pictureMino.changeScreenToGameScreen();
                    return true;
                case Input.Keys.ESCAPE:
                    Gdx.app.exit();
                    return true;
            }
            return false;
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new TitleScreenInputAdapter());
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float secondsSinceLastUpdate) {
        ScreenUtils.clear(0, 0, 0, 1);
        pictureMino.soundManager.stopBgMusic();
        pictureMino.font.setColor(Color.RED);
        pictureMino.batch.begin();
        pictureMino.font.draw(pictureMino.batch, "PictureMino!", 0, PictureMino.HEIGHT / 2f, PictureMino.WIDTH, Align.center, false);
        pictureMino.font.draw(pictureMino.batch, "ENTER to start, ESC to exit", 0, PictureMino.HEIGHT / 3f, PictureMino.WIDTH, Align.center, false);
        pictureMino.batch.end();
    }
}
