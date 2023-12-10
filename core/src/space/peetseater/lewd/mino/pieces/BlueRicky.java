package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the Left L
public class BlueRicky extends Mino {
    public BlueRicky() {
        create(Color.BLUE);
    }

    @Override
    public void getDirection1() {
        // Standard backwards L
        //   o
        //   o
        // o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[2]);
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // L on its back
        //  o o o
        //  o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveRightOf(tmpB[0]);
        tmpB[2].moveLeftOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[2]);
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        // Standard backwards L flipped vertically
        //   o o
        //   o
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[2]);
        updateXY(3);
    }

    @Override
    public void getDirection4() {
        // Standard backwards L on its tiptoe
        //
        //   o 0 o
        //       o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[2]);
        updateXY(4);
    }

    public void setXY(int x, int y) {
        //   o <-- b[1]
        //   o <-- b[0] is our center point
        // o o <-- b[2 and 3]
        b[0].moveOnTo(x, y);
        b[1].moveAbove(b[0]);
        b[2].moveBelow(b[0]);
        b[3].moveLeftOf(b[2]);
        directionToRotate = 1;
    }
}
