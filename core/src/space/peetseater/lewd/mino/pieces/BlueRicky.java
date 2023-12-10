package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the Left L
public class BlueRicky extends Mino {
    public BlueRicky() {
        create(Color.BLUE);
    }

    public void setXY(int x, int y) {
        //   o <-- b[1]
        //   o <-- b[0] is our center point
        // o o <-- b[2 and 3]
        b[0].x = x;
        b[0].y = y;
        b[1].x = x;
        b[1].y = y + Block.SIZE;
        b[2].x = x;
        b[2].y = y - Block.SIZE;
        b[3].x = x - Block.SIZE;
        b[3].y = y - Block.SIZE;
    }

    @Override
    public void updateXY(int direction) {

    }

}
