package space.peetseater.picture.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Disposable;
import org.w3c.dom.Text;

public class BackgroundImageFinder implements Disposable {
    private final FileHandle bgs;
    private final FileHandle[] possibleImages;
    Texture backgroundImage;

    RandomXS128 rng = new RandomXS128();

    public BackgroundImageFinder() {
        bgs = Gdx.files.internal("bgs");
        possibleImages = bgs.list("jpg");
        loadNewImage();
    }

    public void loadNewImage() {
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
