package space.peetseater.picture.mino;

import com.badlogic.gdx.InputAdapter;

public class KeyboardInput extends InputAdapter {

    class HoldableKey {
        public boolean pressed = false;
        public float secondsHeld = 0.0f;
        public boolean beginCounting = false;

        public HoldableKey() {}
        public void update(float seconds) {
            if (beginCounting) {
                secondsHeld += seconds;
            }
        }
        public void reEnableAfter(float threshold) {
            if (secondsHeld >= threshold) {
                pressed = true;
                secondsHeld = 0.0f;
            }
        }

        public void reset() {
            pressed = false;
            secondsHeld = 0.0f;
            beginCounting = false;
        }
    }

    private boolean downPressed, pausePressed = false;
    private boolean quitPressed = false;

    private final HoldableKey rotate = new HoldableKey();
    private final HoldableKey left = new HoldableKey();
    private final HoldableKey right = new HoldableKey();

    private final KeyboardConfiguration keyboardConfiguration;

    public KeyboardInput(KeyboardConfiguration keyboardConfiguration) {
        this.keyboardConfiguration = keyboardConfiguration;
    }

    public void update(float secondsSinceLastFrame) {
        float resetThreshold = 0.5f;
        rotate.update(secondsSinceLastFrame);
        rotate.reEnableAfter(resetThreshold);
        left.update(secondsSinceLastFrame);
        left.reEnableAfter(resetThreshold);
        right.update(secondsSinceLastFrame);
        right.reEnableAfter(resetThreshold);
    }

    public boolean isRotatePressed() {
        return rotate.pressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return left.pressed;
    }

    public boolean isRightPressed() {
        return right.pressed;
    }

    public boolean isQuitPressed() {
        return quitPressed;
    }

    public boolean isPausePressed() {
        return pausePressed;
    }

    public void resetUpPressed() {
        rotate.pressed = false;
    }
    public void resetDownPressed() {
        downPressed = false;
    }
    public void resetRightPressed() {
        right.pressed = false;
    }
    public void resetLeftPressed() {
        left.pressed = false;
    }

    @Override
    public boolean keyDown(int keycode) {
            rotate.pressed = false;
            left.pressed = false;
            right.pressed = false;
            downPressed=false;
            if(keyboardConfiguration.getLeftKey() == keycode) {
                left.pressed = true;
                left.beginCounting = true;
            }
            if(keyboardConfiguration.getRightKey() == keycode) {
                right.pressed = true;
                right.beginCounting = true;
            }
            if(keyboardConfiguration.getRotateKey() == keycode) {
                rotate.pressed = true;
                rotate.beginCounting = true;
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
            left.reset();
        }
        if(keyboardConfiguration.getRightKey() == keycode) {
            right.reset();
        }
        if(keyboardConfiguration.getRotateKey() == keycode) {
            rotate.reset();
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
