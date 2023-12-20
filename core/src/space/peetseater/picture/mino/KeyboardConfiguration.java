package space.peetseater.picture.mino;

import com.badlogic.gdx.Input;

/** Class to allow for remapping of key configuration
 *  By doing this, we could build a screen that allows a user to remap a key as desired.
*  Or they could load up the appropriate inputs from a configuration file or something.
 */
public class KeyboardConfiguration {
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int upKey;
    private int pauseKey;
    private int escapeKey;

    public KeyboardConfiguration() {
        leftKey = Input.Keys.A;
        rightKey = Input.Keys.D;
        upKey = Input.Keys.W;
        downKey = Input.Keys.S;
        pauseKey = Input.Keys.SPACE;
        escapeKey = Input.Keys.ESCAPE;
    }

    public int getDownKey() {
        return downKey;
    }
    public void setDownKey(int key) {
        this.downKey = key;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int key) {
        this.leftKey = key;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getPauseKey() {
        return pauseKey;
    }

    public void setPauseKey(int pauseKey) {
        this.pauseKey = pauseKey;
    }

    public int getEscapeKey() {
        return escapeKey;
    }

    public void setEscapeKey(int escapeKey) {
        this.escapeKey = escapeKey;
    }
}
