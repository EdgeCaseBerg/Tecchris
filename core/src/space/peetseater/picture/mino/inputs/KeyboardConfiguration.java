package space.peetseater.picture.mino.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

/** Class to allow for remapping of key configuration
 *  By doing this, we could build a screen that allows a user to remap a key as desired.
*  Or they could load up the appropriate inputs from a configuration file or something.
 */
public class KeyboardConfiguration {
    private final Preferences preferences;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int rotateKey;
    private int pauseKey;
    private int quitKey;

    private int toggleGhostKey;

    public static String LEFT_PREFERENCE_KEY = "LEFT";
    public static String RIGHT_PREFERENCE_KEY = "RIGHT";
    public static String ROTATE_PREFERENCE_KEY = "ROTATE";
    public static String DOWN_PREFERENCE_KEY = "DOWN";
    public static String PAUSE_PREFERENCE_KEY = "PAUSE";
    public static String QUIT_PREFERENCE_KEY = "QUIT";
    public static String GHOST_PREFERENCE_KEY = "GHOST";


    public KeyboardConfiguration() {
        preferences = Gdx.app.getPreferences("controls");
        leftKey = preferences.getInteger(LEFT_PREFERENCE_KEY, Input.Keys.A);
        rightKey = preferences.getInteger(RIGHT_PREFERENCE_KEY, Input.Keys.D);
        rotateKey = preferences.getInteger(ROTATE_PREFERENCE_KEY, Input.Keys.W);
        downKey = preferences.getInteger(DOWN_PREFERENCE_KEY, Input.Keys.S);
        pauseKey = preferences.getInteger(PAUSE_PREFERENCE_KEY, Input.Keys.SPACE);
        quitKey = preferences.getInteger(QUIT_PREFERENCE_KEY, Input.Keys.ESCAPE);
        toggleGhostKey = preferences.getInteger(GHOST_PREFERENCE_KEY, Input.Keys.G);

    }

    public int getDownKey() {
        return downKey;
    }
    public void setDownKey(int key) {
        this.downKey = key;
        preferences.putInteger(DOWN_PREFERENCE_KEY, key);
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int key) {
        this.leftKey = key;
        preferences.putInteger(LEFT_PREFERENCE_KEY, key);
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
        preferences.putInteger(RIGHT_PREFERENCE_KEY, rightKey);
    }

    public int getRotateKey() {
        return rotateKey;
    }

    public void setRotateKey(int rotateKey) {
        this.rotateKey = rotateKey;
        preferences.putInteger(ROTATE_PREFERENCE_KEY, rotateKey);
    }

    public int getPauseKey() {
        return pauseKey;
    }

    public void setPauseKey(int pauseKey) {
        this.pauseKey = pauseKey;
        preferences.putInteger(PAUSE_PREFERENCE_KEY, pauseKey);
    }

    public int getQuitKey() {
        return quitKey;
    }

    public void setQuitKey(int quitKey) {
        this.quitKey = quitKey;
        preferences.putInteger(QUIT_PREFERENCE_KEY, quitKey);
    }

    public int getToggleGhostKey() {
        return toggleGhostKey;
    }

    public void setToggleGhostKey(int toggleGhostKey) {
        this.toggleGhostKey = toggleGhostKey;
        preferences.putInteger(GHOST_PREFERENCE_KEY, toggleGhostKey);
    }

    public void persistPreferences() {
        preferences.flush();
    }
}
