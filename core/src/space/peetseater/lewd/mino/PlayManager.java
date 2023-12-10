package space.peetseater.lewd.mino;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import space.peetseater.lewd.mino.pieces.*;

public class PlayManager implements Disposable {
    // Main Play Area
    final int PLAY_AREA_WIDTH = 360;
    final int PLAY_AREA_HEIGHT = 600;

    final int NEXT_FRAME_AREA_WIDTH = 200;
    final int NEXT_FRAME_AREA_HEIGHT = 200;

    public static int playAreaLeftX;
    public static int playAreaRightX;
    public static int playAreaTopY;
    public static int playAreaBottomY;

    public static int nextFrameLeftX;
    public static int nextFrameTopY;

    Texture playBg;
    Texture nextPieceFrame;

    BitmapFont font;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    public static float dropIntervalInSeconds = 1f;

    public PlayManager() {
        // TODO Refactor to take this in as parameters instead.
        playAreaLeftX = LewdMino.WIDTH / 2 - PLAY_AREA_WIDTH / 2;
        playAreaRightX = playAreaLeftX + PLAY_AREA_WIDTH;
        playAreaTopY = 50;
        playAreaBottomY = playAreaTopY + PLAY_AREA_HEIGHT;
        playBg = makeRectangleTexture(4, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);

        nextFrameLeftX = playAreaRightX + 100;
        nextFrameTopY = playAreaBottomY - 200;
        nextPieceFrame = makeRectangleTexture(4, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);

        // TODO: Use the distance field free font techniques.
        font = new BitmapFont();
        font.setUseIntegerPositions(true);
        font.setColor(Color.WHITE);

        MINO_START_X = playAreaLeftX + PLAY_AREA_WIDTH / 2 - Block.SIZE;
        MINO_START_Y = playAreaBottomY + Block.SIZE;

        // Testing:
        currentMino = new RhodeIsland();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    /* Kind of a weird thing here where .fillRectangle and .drawRectangle weren't working for me
    * so I just did it myself */
    public Texture makeRectangleTexture(int strokeSize, int width, int height) {
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

    public void update(float timeSinceLastFrame) {
        currentMino.update(timeSinceLastFrame);
    }

    public void render(SpriteBatch batch) {
        int offset = 0;
        batch.draw(playBg, playAreaLeftX - offset, playAreaTopY - offset, PLAY_AREA_WIDTH +offset*2, PLAY_AREA_HEIGHT + offset*2);
        batch.draw(nextPieceFrame, nextFrameLeftX, nextFrameTopY, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);
        font.draw(batch, "NEXT", nextFrameLeftX, nextFrameTopY + 60, NEXT_FRAME_AREA_WIDTH, Align.center, false);

        if (currentMino != null) {
            currentMino.draw(batch);
        }
    }

    public void dispose() {
        font.dispose();
        playBg.dispose();
        nextPieceFrame.dispose();
    }

}
