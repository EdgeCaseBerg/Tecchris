package space.peetseater.picture.mino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.*;
import space.peetseater.picture.mino.*;
import space.peetseater.picture.mino.inputs.KeyboardConfiguration;
import space.peetseater.picture.mino.inputs.KeyboardInput;
import space.peetseater.picture.mino.pieces.*;
import space.peetseater.picture.mino.utils.TextureHelpers;

public class GameScreen extends ScreenAdapter implements Disposable {
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

    public static int winningsAreaLeftX;
    public static int winningsAreaBottomY;

    private final ShaderProgram bgImageShader;
    private Texture winningsBg;
    Texture playBg;
    Texture nextPieceFrame;
    Texture scoreAreaFrame;

    BitmapFont font;

    private Mino currentMino;
    private Mino nextMino;
    private GhostPiece ghostMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    public static float dropIntervalInSeconds = 1f;

    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;

    public static Array<Block> staticBlocks = new Array<>();
    private boolean lineDeleteCounterOn = false;
    private final Array<Integer> lineDeleteEffectsYPositions = new Array<>(4);
    private float lineDeleteEffectSecondsElapsed;
    private final Texture destructionTexture;

    int level = 1;
    int lines = 0;
    int score = 0;
    int linesForReveal = 0;

    public static SoundManager soundManager;

    BackgroundImageFinder bgImageFinder;

    private boolean clearedAtLeastOnce = false;
    private final RandomBag randomBag;

    private final KeyboardInput keyboardInput;
    private boolean gameIsPaused = false;
    private boolean ghostIsEnabled = true;

    private final SpriteBatch batch;
    private final PictureMino pictureMino;

    public GameScreen(PictureMino pictureMino) {
        this.batch = pictureMino.batch;
        this.pictureMino = pictureMino;
        playAreaLeftX = PictureMino.WIDTH / 2 - PLAY_AREA_WIDTH / 2;
        playAreaRightX = playAreaLeftX + PLAY_AREA_WIDTH;
        playAreaBottomY = 50;
        playAreaTopY = playAreaBottomY + PLAY_AREA_HEIGHT;
        playBg = TextureHelpers.makeRectangleTexture(4, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);

        winningsAreaLeftX = playAreaLeftX - PLAY_AREA_WIDTH - 20;
        winningsAreaBottomY = playAreaBottomY;
        winningsBg = TextureHelpers.makeRectangleTexture(4, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);

        nextFrameLeftX = playAreaRightX + 100;
        nextFrameTopY = playAreaTopY - 200;
        nextPieceFrame = TextureHelpers.makeRectangleTexture(4, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);

        destructionTexture = TextureHelpers.makeDestructionTexture(PLAY_AREA_WIDTH, Block.SIZE);

        NEXT_MINO_X = nextFrameLeftX + NEXT_FRAME_AREA_WIDTH / 2;
        NEXT_MINO_Y = nextFrameTopY + NEXT_FRAME_AREA_HEIGHT / 2;

        scoreAreaFrame = TextureHelpers.makeRectangleTexture(4, SCORE_AREA_WIDTH, SCORE_AREA_HEIGHT);
        scoreFrameLeftX = nextFrameLeftX;
        scoreFrameBottomY = playAreaTopY - SCORE_AREA_HEIGHT - NEXT_FRAME_AREA_HEIGHT - 100 + playAreaBottomY;

        font = pictureMino.font;

        MINO_START_X = playAreaLeftX + PLAY_AREA_WIDTH / 2 - Block.SIZE;
        MINO_START_Y = playAreaTopY + Block.SIZE;

        // TODO: We could do some fun stuff loading a conf file here.
        keyboardInput = this.pictureMino.keyboardInput;
        randomBag = new RandomBag();

        currentMino = randomBag.getNextPiece();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = randomBag.getNextPiece();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

        // Start ghost at the bottom of the play area
        ghostMino = new GhostPiece(currentMino);
        ghostMino.setXY(MINO_START_X, playAreaBottomY);

        soundManager = pictureMino.soundManager;

        // Setup background image to be revealed
        bgImageFinder = new BackgroundImageFinder();
        String vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/hideImage.glsl").readString();
        bgImageShader = new ShaderProgram(vertexShader, fragmentShader);
        ShaderProgram.pedantic = false;
        if (!bgImageShader.getLog().isEmpty()) {
            Gdx.app.log("shaders", bgImageShader.getLog());
        }
        if (!bgImageShader.isCompiled()) {
            throw new GdxRuntimeException("Could not compile shader" + bgImageShader.getLog());
        }
    }

    public void update(float timeSinceLastFrame) {
        keyboardInput.update(timeSinceLastFrame);
        if (keyboardInput.isQuitPressed()) {
            this.pictureMino.dispose();
            Gdx.app.exit();
        }
        gameIsPaused = keyboardInput.isPausePressed();
        if (keyboardInput.isPausePressed()) {
            return;
        }
        currentMino.update(timeSinceLastFrame, keyboardInput);
        ghostIsEnabled = keyboardInput.isGhostEnabled();
        if (ghostIsEnabled) {
            ghostMino.moveToCollision(staticBlocks, playAreaBottomY);
        }

        if (!currentMino.active) {
            // Did they fail miserably?
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                // The player didn't move the mino from the starting position, and it
                // bottomed out, therefore, the game is over.
                pictureMino.changeScreenToGameOver(score);
            }

            // Move to the static blocks
            for (int i = 0; i < currentMino.b.length; i++) {
                staticBlocks.add(currentMino.b[i]);
            }
            // Replace current Mino with next
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);

            ghostMino.dispose();
            ghostMino = new GhostPiece(nextMino);

            nextMino = randomBag.getNextPiece();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            // Can we score some points?
            checkAndDeleteLinePossible();

            // Check if user should get a new image
            float revealTo = MathUtils.clamp(Block.SIZE * linesForReveal, 0, PLAY_AREA_HEIGHT);
            if (revealTo == PLAY_AREA_HEIGHT) {
                winningsBg.dispose();
                winningsBg = new Texture(bgImageFinder.getTexture().getTextureData());
                soundManager.playVictory();
                bgImageFinder.loadNewImage();
                linesForReveal = 0;
                clearedAtLeastOnce = true;
            }
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
                boolean canDelete = blocksInRow == PLAY_AREA_WIDTH / Block.SIZE;
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
                    linesForReveal++;
                    linesRemovedAtOnce++;

                    // Increase difficulty / level based on how many lines we've cleared.
                    // Difficulty is capped off at 0.10s
                    if (lines % 10 == 0) {
                        level++;
                        dropIntervalInSeconds -= 0.25f;
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(keyboardInput);
        for (Block b : staticBlocks) {
            b.dispose();
        }
        staticBlocks.clear();
        level = 1;
        lines = 0;
        score = 0;
        linesForReveal = 0;
        soundManager.startBgMusic();
    }

    @Override
    public void render(float timeSinceLastFrame) {
        update(timeSinceLastFrame);
        ScreenUtils.clear(0, 0, 0, 1);


        batch.begin();
        batch.draw(playBg, playAreaLeftX, playAreaBottomY, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);
        float revealTo = MathUtils.clamp(Block.SIZE * linesForReveal, 0, PLAY_AREA_HEIGHT);
        batch.setShader(bgImageShader);
        bgImageShader.setUniformf("u_revealToY", revealTo);
        bgImageShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(
                bgImageFinder.getTexture(),
                playAreaLeftX, playAreaBottomY, // screen x,y
                PLAY_AREA_WIDTH, // stretch to this width
                PLAY_AREA_HEIGHT, // stretch to this height
                0,0,
                bgImageFinder.getTexture().getWidth(),
                bgImageFinder.getTexture().getHeight(),
                false,
                false
        );
        batch.setShader(null);
        batch.draw(winningsBg, winningsAreaLeftX, winningsAreaBottomY, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT);
        batch.draw(nextPieceFrame, nextFrameLeftX, nextFrameTopY, NEXT_FRAME_AREA_WIDTH, NEXT_FRAME_AREA_HEIGHT);
        batch.draw(scoreAreaFrame, scoreFrameLeftX, scoreFrameBottomY, SCORE_AREA_WIDTH, SCORE_AREA_HEIGHT);
        font.setColor(Color.WHITE);
        font.draw(batch, "NEXT", nextFrameLeftX, nextFrameTopY + 60, NEXT_FRAME_AREA_WIDTH, Align.center, false);
        font.draw(batch, "LEVEL: " + level, scoreFrameLeftX, scoreFrameBottomY + 20, SCORE_AREA_WIDTH, Align.center, false);
        font.draw(batch, "LINES: " + lines, scoreFrameLeftX, scoreFrameBottomY + 40, SCORE_AREA_WIDTH, Align.center, false);
        font.draw(batch, "SCORE: " + score, scoreFrameLeftX, scoreFrameBottomY + 60, SCORE_AREA_WIDTH, Align.center, false);
        font.draw(batch, "GHOST: " +  (ghostIsEnabled ? "ON" : "OFF"), scoreFrameLeftX, scoreFrameBottomY + 80, SCORE_AREA_WIDTH, Align.center, false);
        if (!clearedAtLeastOnce) {
            font.draw(batch, "???", winningsAreaLeftX, winningsAreaBottomY + PLAY_AREA_WIDTH / 2f, PLAY_AREA_WIDTH, Align.center, false);
        }

        if (gameIsPaused) {
            font.setColor(Color.YELLOW);
            font.draw(batch, "PAUSED", 0, PictureMino.HEIGHT / 2f, PictureMino.WIDTH, Align.center, false);
        }

        // debug
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            lines++;
            linesForReveal++;
        }

        if (ghostMino != null && ghostIsEnabled) {
            ghostMino.draw(batch);
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

        // Display a line for a short period of time being destroyed
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
        batch.end();
    }

    @Override
    public void pause() {
        gameIsPaused = true;
    }

    @Override
    public void resume() {
        gameIsPaused = false;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
        playBg.dispose();
        nextPieceFrame.dispose();
        bgImageFinder.dispose();
        for (Block staticBlock : staticBlocks) {
            staticBlock.dispose();
        }
        bgImageShader.dispose();
        winningsBg.dispose();
        ghostMino.dispose();
    }

}
