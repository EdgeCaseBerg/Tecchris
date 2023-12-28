package space.peetseater.picture.mino.inputs;

import com.badlogic.gdx.Input;

/** Class to allow for remapping of key configuration
 *  By doing this, we could build a screen that allows a user to remap a key as desired.
*  Or they could load up the appropriate inputs from a configuration file or something.
 */
public class KeyboardConfiguration {
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int rotateKey;
    private int pauseKey;
    private int quitKey;

    private int toggleGhostKey;

    public KeyboardConfiguration() {
        leftKey = Input.Keys.A;
        rightKey = Input.Keys.D;
        rotateKey = Input.Keys.W;
        downKey = Input.Keys.S;
        pauseKey = Input.Keys.SPACE;
        quitKey = Input.Keys.ESCAPE;
        toggleGhostKey = Input.Keys.G;
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

    public int getRotateKey() {
        return rotateKey;
    }

    public void setRotateKey(int rotateKey) {
        this.rotateKey = rotateKey;
    }

    public int getPauseKey() {
        return pauseKey;
    }

    public void setPauseKey(int pauseKey) {
        this.pauseKey = pauseKey;
    }

    public int getQuitKey() {
        return quitKey;
    }

    public void setQuitKey(int quitKey) {
        this.quitKey = quitKey;
    }

    public int getToggleGhostKey() {
        return toggleGhostKey;
    }

    public void setToggleGhostKey(int toggleGhostKey) {
        this.toggleGhostKey = toggleGhostKey;
    }
}
