package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.inputs.KeyboardConfiguration;
import space.peetseater.picture.mino.inputs.SettingScreenInputAdapter;
import space.peetseater.picture.mino.pieces.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * A screen where the user will see the keyboard configuration listed out for them
 * and then they can select which keys do what in the game. */
public class SettingsScreen extends ScreenAdapter {

    private final PictureMino pictureMino;

    private final List<SettingButton> settings;


    public static class SettingButton implements Disposable {
        private final Block activeBlock;
        private final Block inactiveBlock;
        public String text;
        public boolean isActive;

        public KeyboardConfiguration.KeyConf keyConf;

        public SettingButton(String text, KeyboardConfiguration.KeyConf keyConf) {
            activeBlock = new Block(Color.GREEN);
            inactiveBlock = new Block(Color.GRAY);
            this.text = text;
            isActive = false;
            this.keyConf = keyConf;
        }

        public Block getButtonToDisplay() {
            if (isActive) {
                return activeBlock;
            }
            return inactiveBlock;
        }

        public void setSetting(int key) {
            keyConf.set(key);
        }
        public int getSetting() {
            return keyConf.get();
        }

        public String getTextDisplay() {
            // todo memoize
            return this.text + ": (" +Input.Keys.toString(keyConf.get())+ ")";
        }

        public void setActive(boolean b) {
            isActive = b;
        }

        public boolean isPointInside(int screenX, int screenY) {
            return getButtonToDisplay().isPointInside(screenX, screenY);
        }

        public void setBlockTo(int x, int y) {
            activeBlock.moveOnTo(x, y);
            inactiveBlock.moveOnTo(x, y);
        }

        public void dispose() {
            activeBlock.dispose();
            inactiveBlock.dispose();
        }
    }

    public SettingsScreen(final PictureMino pictureMino) {
        this.pictureMino = pictureMino;
        // Make a button for each keyboard configuration input
        settings = new ArrayList<>(6);
        settings.add(
            new SettingButton("Left Key", pictureMino.keyboardConfiguration.getLeftKeyConf())
        );
        settings.add(
            new SettingButton("Right Key", pictureMino.keyboardConfiguration.getRightKeyConf())
        );
        settings.add(
            new SettingButton("Down Key", pictureMino.keyboardConfiguration.getDownKeyConf())
        );
        settings.add(
            new SettingButton("Rotate Key", pictureMino.keyboardConfiguration.getRotateKeyConf())
        );
        settings.add(
            new SettingButton("Pause Key", pictureMino.keyboardConfiguration.getPauseKeyConf())
        );
        settings.add(
            new SettingButton("Ghost Key", pictureMino.keyboardConfiguration.getGhostKeyConf())
        );
        settings.add(
            new SettingButton("Quit Key", pictureMino.keyboardConfiguration.getQuitKeyConf())
        );

        int i = 0;
        for (SettingButton button : settings) {
            button.setBlockTo(PictureMino.WIDTH / 2, PictureMino.HEIGHT / 3 + i * Block.SIZE * 2);
            i++;
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new SettingScreenInputAdapter(settings, pictureMino));
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        // Draw text instructions
        pictureMino.batch.begin();
        pictureMino.font.draw(
            pictureMino.batch,
            "Click on the setting to change, then press the key to assign it to\nWhen done, press ENTER",
            30,
            400
        );

        // Draw each button one after the other
        for (SettingButton button : settings) {
            button.getButtonToDisplay().draw(pictureMino.batch);
            String text = button.getTextDisplay();
            pictureMino.font.draw(
                    pictureMino.batch, text,
                    (float)button.getButtonToDisplay().x + Block.SIZE + 4,
                    (float)button.getButtonToDisplay().y + (Block.SIZE / 2f)
            );
        }

        pictureMino.batch.end();
    }

    @Override
    public void dispose() {
        for(SettingButton button : settings) {
            button.dispose();
        }
    }
}
