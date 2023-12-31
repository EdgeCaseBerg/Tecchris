package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.inputs.TitleScreenInputAdapter;

public class TitleScreen extends ScreenAdapter {
    PictureMino pictureMino;
    public TitleScreen(PictureMino pictureMino) {
        this.pictureMino = pictureMino;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new TitleScreenInputAdapter(pictureMino));
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float secondsSinceLastUpdate) {
        ScreenUtils.clear(0, 0, 0, 1);
        pictureMino.soundManager.stopBgMusic();
        pictureMino.font.setColor(Color.GREEN);
        pictureMino.batch.begin();
        pictureMino.font.draw(pictureMino.batch, "PictureMino!", 0, PictureMino.HEIGHT / 2f, PictureMino.WIDTH, Align.center, false);
        pictureMino.font.draw(pictureMino.batch, "ENTER to start, ESC to exit", 0, PictureMino.HEIGHT / 3f, PictureMino.WIDTH, Align.center, false);
        // TODO: add setting screen button
        pictureMino.batch.end();
    }
}
