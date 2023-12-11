package space.peetseater.lewd.mino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import space.peetseater.lewd.mino.pieces.*;

public class PlayManager implements Disposable {
    // Main Play Area
    final int PLAY_AREA_WIDTH = 360;
    final int PLAY_AREA_HEIGHT = 600;

    final int NEXT_FRAME_AREA_WIDTH = 200;
    final int NEXT_FRAME_AREA_HEIGHT = 200;

    final int SCORE_AREA_WIDTH = 200;
    final int SCORE_AREA_HEIGHT = 300;

    public static int playAreaLeftX;
    public static int playAreaRightX;
    public static int playAreaBottomY;
    public static int playAreaTopY;

    public static int nextFrameLeftX;
    public static int nextFrameTopY;

    public static int scoreFrameLeftX;
    public static int scoreFrameBottomY;
    Texture playBg;
    Texture nextPieceFrame;
    Texture scoreAreaFrame;

    BitmapFont font;

    private Mino currentMino;
    private Mino nextMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    public static float dropIntervalInSeconds = 1f;

    private RandomXS128 rng;

    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;

    public static Array<Block> staticBlocks = new Array<>();
    private boolean lineDeleteCounterOn = false;
    private Array<Integer> lineDeleteEffectsYPositions = new Array<>(4);
    private float lineDeleteEffectSecondsElapsed;
    private Texture destructionTexture;

    private boolean gameOver = false;
    int level = 1;
    int lines = 0;
    int score = 0;

    public static SoundManager soundManager;

    public PlayManager() {
        // TODO Refactor to take this in as parameters instead.
        playAreaLeftX = LewdMino.WIDTH / 2 - PLAY_AREA_WIDTH / 2;
        playAreaRightX = playAreaLeftX + PLAY_AREA_WIDTH;
        playAreaBottomY = 50;
        playAreaTopY = playAreaBottomY + PLAY_AREA_HEIGHT;
        playBg = makeRectangleTexture(4, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);

        nextFrameLeftX = playAreaRightX + 100;
        nextFrameTopY = playAreaTopY - 200;
        nextPieceFrame = makeRectangleTexture(4, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);

        destructionTexture = makeDestructionTexture();

        NEXT_MINO_X = nextFrameLeftX + NEXT_FRAME_AREA_WIDTH / 2;
        NEXT_MINO_Y = nextFrameTopY + NEXT_FRAME_AREA_HEIGHT / 2;

        scoreAreaFrame = makeRectangleTexture(4, SCORE_AREA_WIDTH, SCORE_AREA_HEIGHT);
        scoreFrameLeftX = nextFrameLeftX;
        scoreFrameBottomY = playAreaTopY - SCORE_AREA_HEIGHT - NEXT_FRAME_AREA_HEIGHT - 100 + playAreaBottomY;

        // TODO: Use the distance field free font techniques.
        font = new BitmapFont();
        font.setUseIntegerPositions(true);
        font.setColor(Color.WHITE);

        MINO_START_X = playAreaLeftX + PLAY_AREA_WIDTH / 2 - Block.SIZE;
        MINO_START_Y = playAreaTopY + Block.SIZE;

        rng = new RandomXS128();
        currentMino = getNextRandomPiece();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = getNextRandomPiece();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

        soundManager = new SoundManager();
        soundManager.startBgMusic();
    }

    private Texture makeDestructionTexture() {
        Pixmap pixmap = new Pixmap(PLAY_AREA_WIDTH, Block.SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
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
        if (KeyboardInput.pausePressed || gameOver) {
            return;
        }
        currentMino.update(timeSinceLastFrame);

        if (!currentMino.active) {

            // Did they fail miserably?
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                // The player didn't move the mino from the starting position and it
                // bottomed out, therefore, the game is over.
                gameOver = true;
                soundManager.playGameOver();
            }

            // Move to the static blocks
            for (int i = 0; i < currentMino.b.length; i++) {
                staticBlocks.add(currentMino.b[i]);
            }
            // Replace current Mino with next
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);

            nextMino = getNextRandomPiece();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            // Can we score some points?
            checkAndDeleteLinePossible();
        }
    }

    private void checkAndDeleteLinePossible() {
        // Check if the number of blocks in a single row  are at maximum.
        // TODO: Refactor this inefficient code into an adjacency list or
        // anything that won't have horrific runtime performance because
        // aaaahhhhhhh

        int x = playAreaLeftX;
        int y = playAreaTopY;
        int blocksInRow = 0;
        int linesRemovedAtOnce = 0;

        while(x < playAreaRightX && y >= playAreaBottomY) {
            // I hate how inefficient this is SO SO much
            for (int i = 0; i < staticBlocks.size; i++) {
                Block block = staticBlocks.get(i);
                if (block.x == x && block.y == y) {
                    blocksInRow++;
                }
            }
            x += Block.SIZE;
            if (x == playAreaRightX) {
                // TODO: refactor 12 to computed number.
                boolean canDelete = blocksInRow == 12;
                if (canDelete) {
                    lineDeleteCounterOn = true;
                    lineDeleteEffectsYPositions.add(y);
                    lineDeleteEffectSecondsElapsed = 0f;

                    Array<Block> toRemove = new Array<>(12);
                    for (Block block : staticBlocks) {
                        if (block.y == y) {
                            toRemove.add(block);
                        } else if (block.y > y){
                            // If this was a block that was above the shifted line, move it down
                            block.y -= Block.SIZE;
                        }
                    }
                    for (Block b: toRemove) {
                        staticBlocks.removeValue(b, true);
                    }
                    lines++;
                    linesRemovedAtOnce++;

                    // Increase difficulty / level based on how many lines we've cleared.
                    // Difficulty is capped off at 0.10s
                    if (lines % 10 == 0) {
                        level++;
                        dropIntervalInSeconds -= 0.25;
                        if (dropIntervalInSeconds < 0.10) {
                            dropIntervalInSeconds = 0.10f;
                        }
                    }
                }
                blocksInRow = 0;
                x = playAreaLeftX;
                y -= Block.SIZE;
            }
        }

        if (linesRemovedAtOnce > 0) {
            int singleLineScore = 10 * level;
            score += singleLineScore * linesRemovedAtOnce;
            soundManager.playLineDeletes();
        }
    }

    public void render(SpriteBatch batch, float timeSinceLastFrame) {

        if (gameOver) {
            font.setColor(Color.RED);
            font.draw(batch, "Game Over", 0, LewdMino.HEIGHT / 2, LewdMino.WIDTH, Align.center, false);
            soundManager.stopBgMusic();
            return;
        }

        int offset = 0;
        batch.draw(playBg, playAreaLeftX - offset, playAreaBottomY - offset, PLAY_AREA_WIDTH +offset*2, PLAY_AREA_HEIGHT + offset*2);
        batch.draw(nextPieceFrame, nextFrameLeftX, nextFrameTopY, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);
        batch.draw(scoreAreaFrame, scoreFrameLeftX, scoreFrameBottomY, SCORE_AREA_WIDTH, SCORE_AREA_HEIGHT);
        font.setColor(Color.WHITE);
        font.draw(batch, "NEXT", nextFrameLeftX, nextFrameTopY + 60, NEXT_FRAME_AREA_WIDTH, Align.center, false);
        font.draw(batch, "LEVEL: " + level, scoreFrameLeftX, scoreFrameBottomY + 20, SCORE_AREA_WIDTH, Align.center, false);
        font.draw(batch, "LINES: " + lines, scoreFrameLeftX, scoreFrameBottomY + 40, SCORE_AREA_WIDTH, Align.center, false);
        font.draw(batch, "SCORE: " + score, scoreFrameLeftX, scoreFrameBottomY + 60, SCORE_AREA_WIDTH, Align.center, false);

        if (KeyboardInput.pausePressed) {
            font.setColor(Color.YELLOW);
            font.draw(batch, "PAUSED", 0, LewdMino.HEIGHT / 2, LewdMino.WIDTH, Align.center, false);
        }

        if (currentMino != null) {
            currentMino.draw(batch);
        }

        if (nextMino != null) {
            nextMino.draw(batch);
        }

        for (int i = 0; i < staticBlocks.size; i++) {
            staticBlocks.get(i).draw(batch);
        }

        if (lineDeleteCounterOn) {
            for (Integer y: lineDeleteEffectsYPositions) {
                 batch.draw(destructionTexture, playAreaLeftX, y, PLAY_AREA_WIDTH, Block.SIZE);
            }
            lineDeleteEffectSecondsElapsed += timeSinceLastFrame;
            if (lineDeleteEffectSecondsElapsed >= 0.25) {
                lineDeleteEffectsYPositions.clear();
                lineDeleteCounterOn = false;
                lineDeleteEffectSecondsElapsed = 0f;
            }
        }
    }

    public void dispose() {
        font.dispose();
        playBg.dispose();
        nextPieceFrame.dispose();
    }

    public Mino getNextRandomPiece() {
        Mino mino = null;
        int r = rng.nextInt(7);
        switch (r) {
            case 0: mino =  new BlueRicky();    break;
            case 1: mino =  new OrangeRicky();  break;
            case 2: mino =  new RhodeIsland();  break;
            case 3: mino =  new ClevelandZ();   break;
            case 4: mino =  new Smashboy();     break;
            case 5: mino =  new Hero();         break;
            case 6: mino =  new Teewee();       break;
        }
        return mino;
    }

}
