package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.peetseater.picture.mino.KeyboardInput;
import space.peetseater.picture.mino.PlayManager;

abstract public class Mino {

    public Block[] b = new Block[4];
    public Block[] tmpB = new Block[4];

    public MinoRotation directionToRotate = MinoRotation.ZERO;

    protected boolean leftCollision, rightCollision, bottomCollision;

    public boolean active = true;
    public boolean isDeactivating = false;
    float deactivatingTimer = 0;

    float dropTimeAccumulator = 0;


    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            if (b[i] != null) {
                b[i].dispose();
            }
            if (tmpB[i] != null) {
                tmpB[i].dispose();
            }
            b[i] = new Block(c);
            tmpB[i] = new Block(c);
        }
    }

    public abstract void setXY(int x, int y);

    public abstract void getRotation0Degrees();
    public abstract void getRotation90Degrees();
    public abstract void getRotation180Degrees();
    public abstract void getRotation270Degrees();

    public void checkMovementCollision() {
        leftCollision=rightCollision=bottomCollision=false;

        checkStaticBlockCollision();

        for (Block block : b) {
            if (block.x <= PlayManager.playAreaLeftX) {
                leftCollision = true;
            }
            if (block.x + Block.SIZE >= PlayManager.playAreaRightX) {
                rightCollision = true;
            }
            // You'd normally think of this as the bottom but
            // since in libgdx we draw from the top down,
            if (block.y <= PlayManager.playAreaBottomY) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision=rightCollision=bottomCollision=false;

        checkStaticBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if (tmpB[i].x < PlayManager.playAreaLeftX) {
                leftCollision = true;
            }
            if (tmpB[i].x + Block.SIZE > PlayManager.playAreaRightX) {
                rightCollision = true;
            }
            // You'd normally think of this as the bottom but
            // since in libgdx we draw from the top down,
            if (tmpB[i].y < PlayManager.playAreaBottomY) {
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision() {
        // TODO: THIS IS SO INEFFICIENT AND LEAKY OH GOD WHY OH GOD
        for (int i = 0; i < PlayManager.staticBlocks.size; i++) {
            Block sb = PlayManager.staticBlocks.get(i);
            int targetX = sb.x;
            int targetY = sb.y;

            for (Block block : b) {
                if (block.y - Block.SIZE == targetY && block.x == targetX) {
                    bottomCollision = true;
                }
                if (block.x - Block.SIZE == targetX && block.y == targetY) {
                    leftCollision = true;
                }
                if (block.x + Block.SIZE == targetX && block.y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void updateXY(MinoRotation direction) {
        checkRotationCollision();

        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.directionToRotate = direction;
            for (int i = 0; i < b.length; i++) {
                b[i].x = tmpB[i].x;
                b[i].y = tmpB[i].y;
            }
        }
    }

    public void update (float timeSinceLastFrame) {
        // Let's not overrun an integer.
        if (isDeactivating) {
            deactivatingTimer += timeSinceLastFrame;
            deactivating(timeSinceLastFrame);
        }
        if (active) {
            dropTimeAccumulator += timeSinceLastFrame;
        }

        // TODO: Oh god oh god oh god
        if (KeyboardInput.upPressed) {
            switch (directionToRotate) {
                case ZERO:
                    getRotation90Degrees();
                    break;
                case NINETY:
                    getRotation180Degrees();
                    break;
                case ONEEIGHTY:
                    getRotation270Degrees();
                    break;
                default:
                    getRotation0Degrees();
                    break;
            }
            PlayManager.soundManager.playRotate();
            KeyboardInput.upPressed = false;
            dropTimeAccumulator = 0;
        }

        checkMovementCollision();

        if (KeyboardInput.downPressed) {
            if (!bottomCollision) {
                for (Block block : b) {
                    block.y -= Block.SIZE;
                }
                dropTimeAccumulator = 0;
            }
            KeyboardInput.downPressed = false;
        }
        if (KeyboardInput.leftPressed) {
            if (!leftCollision) {
                for (Block block : b) {
                    block.x -= Block.SIZE;
                }
            }
            KeyboardInput.leftPressed = false;
        }
        if (KeyboardInput.rightPressed) {
            if (!rightCollision) {
                for (Block block : b) {
                    block.x += Block.SIZE;
                }
            }
            KeyboardInput.rightPressed = false;
        }

        // TODO: This feels pretty gross that these blocks are aware of the playmanager
        // so its probably a good candidate to revisit after I finish this tutorial.
        if (bottomCollision) {
            // Avoid playing sound multiple times while sliding
            if (!isDeactivating) {
                PlayManager.soundManager.playTouchFloor();
            }
            isDeactivating = true;
        } else {
            if (dropTimeAccumulator >= PlayManager.dropIntervalInSeconds) {
                for (Block block : b) {
                    block.y -= Block.SIZE;
                }
                dropTimeAccumulator = 0;
            }
        }
    }

    private void deactivating(float timeSinceLastFrame) {
        deactivatingTimer += timeSinceLastFrame;
        // Half a second grace period to allow for sliding behavior
        if (deactivatingTimer >= 0.75) {
            deactivatingTimer = 0;
            // Are we hitting the bottom?
            checkMovementCollision();

            if (bottomCollision) {
                active = false;
                isDeactivating = false;
            }
        }

    }

    public void draw(SpriteBatch batch) {
        for (Block block : b) {
            block.draw(batch);
        }
    }

}
