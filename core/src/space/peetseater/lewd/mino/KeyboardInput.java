package space.peetseater.lewd.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardInput {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed = false;

    public void update() {
        // Reset
        leftPressed=rightPressed=upPressed=downPressed=false;

        // what was pressed?
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            leftPressed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rightPressed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            upPressed = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            downPressed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pausePressed = !pausePressed;
        }
    }
}
