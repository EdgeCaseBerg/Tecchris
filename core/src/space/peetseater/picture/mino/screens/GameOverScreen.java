package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.inputs.GameOverScreenInputAdapter;

public class GameOverScreen extends ScreenAdapter {
    private int score;
    PictureMino pictureMino;
    public GameOverScreen(PictureMino pictureMino) {
        this.pictureMino = pictureMino;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameOverScreenInputAdapter(pictureMino));
        pictureMino.soundManager.playGameOver();
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
        pictureMino.font.draw(pictureMino.batch, "Game Over", 0, PictureMino.HEIGHT / 2f, PictureMino.WIDTH, Align.center, false);
        pictureMino.font.draw(pictureMino.batch, "Final Score: " + score, 0, PictureMino.HEIGHT / 3f, PictureMino.WIDTH, Align.center, false);
        pictureMino.font.draw(pictureMino.batch, "ENTER to restart, ESC to exit", 0, PictureMino.HEIGHT / 4f, PictureMino.WIDTH, Align.center, false);
        pictureMino.batch.end();
    }
}
