package space.peetseater.picture.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable {
    private final Sound gameOverSound;
    private final Sound touchFloorSound;
    private final Music korobeiniki;
    Sound rotateSound;
    Sound deleteLineSound;

    private final Sound victory;



    // Todo, add bg music

    public SoundManager() {
        rotateSound = Gdx.audio.newSound(Gdx.files.internal("rotation.wav"));
        deleteLineSound = Gdx.audio.newSound(Gdx.files.internal("delete line.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));
        touchFloorSound = Gdx.audio.newSound(Gdx.files.internal("touch floor.wav"));
        victory = Gdx.audio.newSound(Gdx.files.internal("Victory.wav"));
        korobeiniki = Gdx.audio.newMusic(Gdx.files.internal("BeepBox-Korobeiniki.wav"));
        korobeiniki.setLooping(true);
    }

    public void startBgMusic() {
        korobeiniki.play();
    }
    public void stopBgMusic() {
        korobeiniki.stop();
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

    public void playVictory() {
        victory.play();
    }

    @Override
    public void dispose() {
        rotateSound.dispose();
        deleteLineSound.dispose();
        gameOverSound.dispose();
        touchFloorSound.dispose();
        korobeiniki.dispose();
        victory.dispose();
    }


}
