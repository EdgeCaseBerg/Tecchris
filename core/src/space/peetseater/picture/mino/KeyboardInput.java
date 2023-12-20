package space.peetseater.picture.mino;

import com.badlogic.gdx.InputAdapter;

public class KeyboardInput extends InputAdapter {

    private boolean rotatePressed, downPressed, leftPressed, rightPressed, pausePressed = false;
    private boolean quitPressed = false;

    private final KeyboardConfiguration keyboardConfiguration;

    public KeyboardInput(KeyboardConfiguration keyboardConfiguration) {
        this.keyboardConfiguration = keyboardConfiguration;
    }

    public boolean isRotatePressed() {
        return rotatePressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isQuitPressed() {
        return quitPressed;
    }

    public boolean isPausePressed() {
        return pausePressed;
    }

    public void resetUpPressed() {
        rotatePressed = false;
    }
    public void resetDownPressed() {
        downPressed = false;
    }
    public void resetRightPressed() {
        rightPressed = false;
    }
    public void resetLeftPressed() {
        leftPressed = false;
    }

    @Override
    public boolean keyDown(int keycode) {
            leftPressed=rightPressed= rotatePressed =downPressed=false;
            if(keyboardConfiguration.getLeftKey() == keycode) {
                leftPressed = true;
            }
            if(keyboardConfiguration.getRightKey() == keycode) {
                rightPressed = true;
            }
            if(keyboardConfiguration.getRotateKey() == keycode) {
                rotatePressed = true;
            }
            if (keyboardConfiguration.getDownKey() == keycode) {
                downPressed = true;
            }
            if (keyboardConfiguration.getQuitKey() == keycode) {
                quitPressed = true;
            }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keyboardConfiguration.getLeftKey() == keycode) {
            leftPressed = false;
        }
        if(keyboardConfiguration.getRightKey() == keycode) {
            rightPressed = false;
        }
        if(keyboardConfiguration.getRotateKey() == keycode) {
            rotatePressed = false;
        }
        if (keyboardConfiguration.getDownKey() == keycode) {
            downPressed = false;
        }
        if (keyboardConfiguration.getQuitKey() == keycode) {
            quitPressed = false;
        }
        if (keyboardConfiguration.getPauseKey() == keycode) {
            /* The pause key is only handed here because we want it to be a toggle.
             *  so there isn't a corresponding case in the keydown method
             */
            pausePressed = !pausePressed;
        }

        return true;
    }

}
