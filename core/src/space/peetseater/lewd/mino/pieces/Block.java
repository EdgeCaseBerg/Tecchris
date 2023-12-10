package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/* Every tetromino is composed of 4 blocks
* So, this block is our base component */
public class Block implements Disposable {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;
    Texture texture;

    public Block(Color c){
        this.color = c;
        fill();
    }

    public void moveAbove(Block b) {
        this.x = b.x;
        this.y = b.y + SIZE;
    }
    public void moveBelow(Block b) {
        this.x = b.x;
        this.y = b.y - SIZE;
    }
    public void moveLeftOf(Block b) {
        this.x = b.x - SIZE;
        this.y = b.y;
    }
    public void moveRightOf(Block b) {
        this.x = b.x + SIZE;
        this.y = b.y;
    }
    public void moveOnTo(Block b) {
        this.x = b.x;
        this.y = b.y;
    }
    public void moveOnTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fill() {
        if (texture != null) {
            texture.dispose();
        }
        Pixmap pixmap = new Pixmap(SIZE, SIZE, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
        // Add a thin border around the block
        pixmap.setColor(color.BLACK);
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (x < 2 || x > SIZE - 3) {
                    pixmap.drawPixel(x, y);
                } else if (y < 2 || y > SIZE - 3) {
                    pixmap.drawPixel(x, y);
                }
            }
        }
        texture = new Texture(pixmap);
        pixmap.dispose();
    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, SIZE, SIZE);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }


}
