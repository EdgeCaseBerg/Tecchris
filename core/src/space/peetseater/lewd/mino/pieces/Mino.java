package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.peetseater.lewd.mino.PlayManager;

abstract public class Mino {

    public Block b[] = new Block[4];
    public Block tmpB[] = new Block[4];

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
    public abstract void updateXY(int direction);
    public void update (float timeSinceLastFrame) {
        dropTimeAccumulator += timeSinceLastFrame;
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
