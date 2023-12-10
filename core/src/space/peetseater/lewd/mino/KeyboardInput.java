package space.peetseater.lewd.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardInput {

    public static boolean upPressed, downPressed, leftPressed, rightPressed;

    public void update() {
        // Reset
        leftPressed=rightPressed=upPressed=downPressed=false;

        // what was pressed?
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            leftPressed = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rightPressed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            upPressed = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            downPressed = true;
        }
    }
}
