package space.peetseater.picture.mino.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.screens.SettingsScreen;

import java.util.List;

public class SettingScreenInputAdapter extends InputAdapter {

    private final List<SettingsScreen.SettingButton> buttons;
    private final PictureMino pictureMino;

    public SettingScreenInputAdapter(List<SettingsScreen.SettingButton> buttons, PictureMino pictureMino) {
        this.buttons = buttons;
        this.pictureMino = pictureMino;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            pictureMino.keyboardConfiguration.persistPreferences();
            pictureMino.changeScreenToTitle();
            return true;
        }
        for(SettingsScreen.SettingButton button : buttons) {
            if (button.isActive) {
                button.setSetting(keycode);
                button.setActive(false);
                return true;
            }
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int mousebutton) {
        int tx = screenX;
        int ty = Gdx.graphics.getHeight() - screenY;
        boolean wasActivated = false;
        for(SettingsScreen.SettingButton button : buttons) {
            boolean activated = button.isPointInside(tx, ty);
            button.setActive(activated);
            wasActivated = wasActivated || activated;
        }
        if (wasActivated) {
            return true;
        }
        return super.touchUp(screenX, screenY, pointer, mousebutton);
    }
}