package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.peetseater.lewd.mino.KeyboardInput;
import space.peetseater.lewd.mino.PlayManager;

import java.security.Key;

abstract public class Mino {

    public Block b[] = new Block[4];
    public Block tmpB[] = new Block[4];

    public  int directionToRotate = 1; // 1/2/3/4 // TODO REFACTOR THIS TO ENUM

    float dropTimeAccumulator = 0;

    public abstract void getDirection1();
    public abstract void getDirection2();
    public abstract void getDirection3();
    public abstract void getDirection4();

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
    public void updateXY(int direction) {
        this.directionToRotate = direction;
        for (int i = 0; i < b.length; i++) {
            b[i].x = tmpB[i].x;
            b[i].y = tmpB[i].y;
        }
    };
    public void update (float timeSinceLastFrame) {
        dropTimeAccumulator += timeSinceLastFrame;

        // TODO: Oh god oh god oh god
        if (KeyboardInput.upPressed) {
            switch (directionToRotate) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                default:
                    getDirection1();
                    break;
            }
            KeyboardInput.upPressed = false;
            dropTimeAccumulator = 0;
        }
        if (KeyboardInput.downPressed) {
            for (int i = 0; i < b.length; i++) {
                b[i].y -= Block.SIZE;
            }
            dropTimeAccumulator = 0;
            KeyboardInput.downPressed = false;
        }
        if (KeyboardInput.leftPressed) {
            for (int i = 0; i < b.length; i++) {
                b[i].x -= Block.SIZE;
            }
            KeyboardInput.leftPressed = false;
        }
        if (KeyboardInput.rightPressed) {
            for (int i = 0; i < b.length; i++) {
                b[i].x += Block.SIZE;
            }
            KeyboardInput.rightPressed = false;
        }

        // TODO: This feels pretty gross that these blocks are aware of the playmanager
        // so its probably a good candidate to revisit after I finish this tutorial.
        if (dropTimeAccumulator >= PlayManager.dropIntervalInSeconds) {
            for (int i = 0; i < b.length; i++) {
                b[i].y -= Block.SIZE;
            }
            dropTimeAccumulator = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < b.length; i++) {
            b[i].draw(batch);
        }
    }

}
