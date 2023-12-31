package space.peetseater.picture.mino.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import space.peetseater.picture.mino.PictureMino;

public class GameOverScreenInputAdapter extends InputAdapter {

    private final PictureMino pictureMino;

    public GameOverScreenInputAdapter(PictureMino pictureMino) {
        this.pictureMino = pictureMino;
    }
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