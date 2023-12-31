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
    private KeyConf downKey;
    private KeyConf leftKey;
    private KeyConf rightKey;
    private KeyConf rotateKey;
    private KeyConf pauseKey;
    private KeyConf quitKey;

    private KeyConf toggleGhostKey;

    public static String LEFT_PREFERENCE_KEY = "LEFT";
    public static String RIGHT_PREFERENCE_KEY = "RIGHT";
    public static String ROTATE_PREFERENCE_KEY = "ROTATE";
    public static String DOWN_PREFERENCE_KEY = "DOWN";
    public static String PAUSE_PREFERENCE_KEY = "PAUSE";
    public static String QUIT_PREFERENCE_KEY = "QUIT";
    public static String GHOST_PREFERENCE_KEY = "GHOST";

    public KeyConf getLeftKeyConf() {
        return this.leftKey;
    }

    public KeyConf getRightKeyConf() {
        return this.rightKey;
    }

    public KeyConf getDownKeyConf() {
        return this.downKey;
    }

    public KeyConf getRotateKeyConf() {
        return this.rotateKey;
    }

    public KeyConf getPauseKeyConf() {
        return this.pauseKey;
    }

    public KeyConf getGhostKeyConf() {
        return this.toggleGhostKey;
    }

    public KeyConf getQuitKeyConf() {
        return this.quitKey;
    }

    public static class KeyConf {
        private final String name;
        private int code;

        public KeyConf(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public void set(int code) {
            this.code = code;
        }
        public int get() {
            return this.code;
        }

        public void persistIn(Preferences preferences) {
            preferences.putInteger(name, code);
        }
    }


    public KeyboardConfiguration() {
        preferences = Gdx.app.getPreferences("controls");
        leftKey = new KeyConf(LEFT_PREFERENCE_KEY, preferences.getInteger(LEFT_PREFERENCE_KEY, Input.Keys.A));
        rightKey = new KeyConf(RIGHT_PREFERENCE_KEY, preferences.getInteger(RIGHT_PREFERENCE_KEY, Input.Keys.D));
        rotateKey = new KeyConf(ROTATE_PREFERENCE_KEY, preferences.getInteger(ROTATE_PREFERENCE_KEY, Input.Keys.W));
        downKey = new KeyConf(DOWN_PREFERENCE_KEY, preferences.getInteger(DOWN_PREFERENCE_KEY, Input.Keys.S));
        pauseKey = new KeyConf(PAUSE_PREFERENCE_KEY, preferences.getInteger(PAUSE_PREFERENCE_KEY, Input.Keys.SPACE));
        quitKey = new KeyConf(QUIT_PREFERENCE_KEY, preferences.getInteger(QUIT_PREFERENCE_KEY, Input.Keys.ESCAPE));
        toggleGhostKey = new KeyConf(GHOST_PREFERENCE_KEY, preferences.getInteger(GHOST_PREFERENCE_KEY, Input.Keys.G));

    }

    public int getDownKey() {
        return downKey.get();
    }
    public void setDownKey(int key) {
        this.downKey.set(key);
        this.downKey.persistIn(preferences);
    }

    public int getLeftKey() {
        return leftKey.get();
    }

    public void setLeftKey(int key) {
        this.leftKey.set(key);
        this.leftKey.persistIn(preferences);
    }

    public int getRightKey() {
        return rightKey.get();
    }

    public void setRightKey(int rightKey) {
        this.rightKey.set(rightKey);
        this.rightKey.persistIn(preferences);
    }

    public int getRotateKey() {
        return rotateKey.get();
    }

    public void setRotateKey(int rotateKey) {
        this.rotateKey.set(rotateKey);
        this.rotateKey.persistIn(preferences);
    }

    public int getPauseKey() {
        return pauseKey.get();
    }

    public void setPauseKey(int pauseKey) {
        this.pauseKey.set(pauseKey);
        this.pauseKey.persistIn(preferences);
    }

    public int getQuitKey() {
        return quitKey.get();
    }

    public void setQuitKey(int quitKey) {
        this.quitKey.set(quitKey);
        this.quitKey.persistIn(preferences);
    }

    public int getToggleGhostKey() {
        return toggleGhostKey.get();
    }

    public void setToggleGhostKey(int toggleGhostKey) {
        this.toggleGhostKey.set(toggleGhostKey);
        this.toggleGhostKey.persistIn(preferences);
    }

    public void persistPreferences() {
        // Possibly future refactor:
        // Consider if making the keyconf be more aware of the preferences would be
        // worth the maintenance of this list of calls...
        this.pauseKey.persistIn(preferences);
        this.downKey.persistIn(preferences);
        this.leftKey.persistIn(preferences);
        this.rightKey.persistIn(preferences);
        this.rotateKey.persistIn(preferences);
        this.quitKey.persistIn(preferences);
        this.toggleGhostKey.persistIn(preferences);
        preferences.flush();
    }
}
