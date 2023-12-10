package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the Left L
public class BlueRicky extends Mino {
    public BlueRicky() {
        create(Color.BLUE);
    }

    @Override
    public void getDirection1() {
        int centerX = b[0].x;
        int centerY = b[0].y;

        // Standard backwards L
        //   o
        //   o
        // o o
        tmpB[0].x = centerX;
        tmpB[0].y = centerY;
        tmpB[1].x = centerX;
        tmpB[1].y = centerY + Block.SIZE;
        tmpB[2].x = centerX;
        tmpB[2].y = centerY - Block.SIZE;
        tmpB[3].x = centerX - Block.SIZE;
        tmpB[3].y = centerY - Block.SIZE;
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        int centerX = b[0].x;
        int centerY = b[0].y;

        // L on its back
        //  o o o
        //  o
        tmpB[0].x = centerX;
        tmpB[0].y = centerY;
        tmpB[1].x = centerX + Block.SIZE;
        tmpB[1].y = centerY;
        tmpB[2].x = centerX - Block.SIZE;
        tmpB[2].y = centerY;
        tmpB[3].x = centerX - Block.SIZE;
        tmpB[3].y = centerY + Block.SIZE;
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        int centerX = b[0].x;
        int centerY = b[0].y;

        // Standard backwards L flipped vertically
        //   o o
        //   o
        //   o
        tmpB[0].x = centerX;
        tmpB[0].y = centerY;
        tmpB[1].x = centerX;
        tmpB[1].y = centerY + Block.SIZE;
        tmpB[2].x = centerX;
        tmpB[2].y = centerY - Block.SIZE;
        tmpB[3].x = centerX + Block.SIZE;
        tmpB[3].y = centerY + Block.SIZE;
        updateXY(3);
    }

    @Override
    public void getDirection4() {
        int centerX = b[0].x;
        int centerY = b[0].y;

        // Standard backwards L on its tiptoe
        //
        //   o 0 o
        //       o
        tmpB[0].x = centerX;
        tmpB[0].y = centerY;
        tmpB[1].x = centerX - Block.SIZE;
        tmpB[1].y = centerY;
        tmpB[2].x = centerX + Block.SIZE;
        tmpB[2].y = centerY;
        tmpB[3].x = centerX + Block.SIZE;
        tmpB[3].y = centerY - Block.SIZE;
        updateXY(4);
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

        // TODO tempting to just call getDirection1
    }
}
