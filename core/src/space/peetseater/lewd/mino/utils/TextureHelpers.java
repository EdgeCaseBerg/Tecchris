package space.peetseater.lewd.mino.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import space.peetseater.lewd.mino.pieces.Block;

public class TextureHelpers {

    /* Kind of a weird thing here where .fillRectangle and .drawRectangle weren't working for me
     * so I just did it myself */
    public static Texture makeRectangleTexture(int strokeSize, int width, int height) {
        Pixmap pixmapBg =  new Pixmap(width, height, Pixmap.Format.RGB888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < strokeSize + 1 || x > width - strokeSize - 1) {
                    pixmapBg.setColor(Color.WHITE);
                } else if (y < strokeSize + 1 || y > height - strokeSize - 1) {
                    pixmapBg.setColor(Color.WHITE);
                } else {
                    pixmapBg.setColor(Color.BLACK);
                }
                pixmapBg.drawPixel(x, y);
            }
        }
        Texture t = new Texture(pixmapBg);
        pixmapBg.dispose();
        return t;
    }

    public static Texture makeDestructionTexture(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

}
