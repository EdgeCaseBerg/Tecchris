package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.inputs.SettingScreenInputAdapter;
import space.peetseater.picture.mino.inputs.SettingSetGet;
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

        public SettingSetGet setGet;

        public SettingButton(String text, SettingSetGet settingSetGet) {
            activeBlock = new Block(Color.GREEN);
            inactiveBlock = new Block(Color.GRAY);
            this.text = text;
            isActive = false;
            setGet = settingSetGet;
        }

        public Block getButtonToDisplay() {
            if (isActive) {
                return activeBlock;
            }
            return inactiveBlock;
        }

        public void setSetting(int key) {
            setGet.set(key);
            ;
        }
        public int getSetting() {
            return setGet.get();
        }

        public String getTextDisplay() {
            // todo memoize
            return this.text + ": (" +Input.Keys.toString(setGet.get())+ ")";
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
            new SettingButton("Left Key",
                new SettingSetGet() {
                    @Override
                    public void set(int key) {
                        pictureMino.keyboardConfiguration.setLeftKey(key);
                    }

                    @Override
                    public int get() {
                        return pictureMino.keyboardConfiguration.getLeftKey();
                    }
                }
            )
        );
        settings.add(
            new SettingButton(
                "Right Key",
                new SettingSetGet() {
                    @Override
                    public void set(int key) {
                        pictureMino.keyboardConfiguration.setRightKey(key);
                    }

                    @Override
                    public int get() {
                        return pictureMino.keyboardConfiguration.getRightKey();
                    }
                }
            )
        );
        settings.add(
            new SettingButton(
                "Down Key",
                new SettingSetGet() {
                    @Override
                    public void set(int key) {
                        pictureMino.keyboardConfiguration.setDownKey(key);
                    }

                    @Override
                    public int get() {
                        return pictureMino.keyboardConfiguration.getDownKey();
                    }
                }
            )
        );
        settings.add(
            new SettingButton(
                "Rotate Key",
                new SettingSetGet() {
                    @Override
                    public void set(int key) {
                        pictureMino.keyboardConfiguration.setRotateKey(key);
                    }

                    @Override
                    public int get() {
                        return pictureMino.keyboardConfiguration.getRotateKey();
                    }
                }
            )
        );
        settings.add(
            new SettingButton("Pause Key", new SettingSetGet() {
                @Override
                public void set(int key) {
                    pictureMino.keyboardConfiguration.setPauseKey(key);
                }

                @Override
                public int get() {
                    return pictureMino.keyboardConfiguration.getPauseKey();
                }
            })
        );
        settings.add(
            new SettingButton("Ghost Key", new SettingSetGet() {
                @Override
                public void set(int key) {
                    pictureMino.keyboardConfiguration.setToggleGhostKey(key);
                }

                @Override
                public int get() {
                    return pictureMino.keyboardConfiguration.getToggleGhostKey();
                }
            })
        );
        settings.add(
            new SettingButton("Quit Key", new SettingSetGet() {
                @Override
                public void set(int key) {
                    pictureMino.keyboardConfiguration.setQuitKey(key);
                }

                @Override
                public int get() {
                    return pictureMino.keyboardConfiguration.getQuitKey();
                }
            })
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
