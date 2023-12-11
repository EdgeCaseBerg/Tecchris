package space.peetseater.lewd.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Disposable;
import org.w3c.dom.Text;

public class BackgroundImageFinder implements Disposable {
    private final FileHandle bgs;
    Texture backgroundImage;

    RandomXS128 rng = new RandomXS128();

    public BackgroundImageFinder() {
        bgs = Gdx.files.internal("bgs");
        loadNewImage();
    }

    public void loadNewImage() {
        FileHandle[] possibleImages = bgs.list("png");
        int toLoad = rng.nextInt(possibleImages.length);
        if (backgroundImage != null) {
            backgroundImage.dispose();
        }
        backgroundImage = new Texture(possibleImages[toLoad]);
    }

    public Texture getTexture() {
        return backgroundImage;
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
    }
}
