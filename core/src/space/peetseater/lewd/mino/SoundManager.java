package space.peetseater.lewd.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable {
    private final Sound gameOverSound;
    private final Sound touchFloorSound;
    Sound rotateSound;
    Sound deleteLineSound;

    // Todo, add bg music

    public SoundManager() {
        rotateSound = Gdx.audio.newSound(Gdx.files.internal("rotation.wav"));
        deleteLineSound = Gdx.audio.newSound(Gdx.files.internal("delete line.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));
        touchFloorSound = Gdx.audio.newSound(Gdx.files.internal("touch floor.wav"));
    }

    public void playRotate() {
        rotateSound.play();
    }
    public void playLineDeletes() {
        deleteLineSound.play();
    }
    public void playTouchFloor() {
        touchFloorSound.play();
    }
    public void playGameOver() {
        gameOverSound.play();
    }

    @Override
    public void dispose() {
        rotateSound.dispose();
    }


}
