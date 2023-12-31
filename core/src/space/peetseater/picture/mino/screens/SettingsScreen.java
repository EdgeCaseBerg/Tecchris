package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import space.peetseater.picture.mino.PictureMino;
import space.peetseater.picture.mino.pieces.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * A screen where the user will see the keyboard configuration listed out for them
 * and then they can select which keys do what in the game. */
public class SettingsScreen extends ScreenAdapter {

    private final PictureMino pictureMino;

    private List<SettingButton> settings;

    class SettingScreenInputAdapter extends InputAdapter {

        private final List<SettingButton> buttons;

        public SettingScreenInputAdapter(List<SettingButton> buttons) {
            this.buttons = buttons;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.ENTER) {
                pictureMino.keyboardConfiguration.persistPreferences();
                pictureMino.changeScreenToTitle();
                return true;
            }
            for(SettingButton button : buttons) {
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
            Gdx.app.log("TOUCH", "(" + screenX + "," + screenY + ")");
            int tx = screenX;
            int ty = Gdx.graphics.getHeight() - screenY;
            boolean wasActivated = false;
            for(SettingButton button : buttons) {
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



    interface SettingSetGet {
        public void set(int key);
        public int get();
    }

    class SettingButton {
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
        Gdx.input.setInputProcessor(new SettingScreenInputAdapter(settings));
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
}
