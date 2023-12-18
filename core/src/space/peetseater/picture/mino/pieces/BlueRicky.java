package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the Left L
public class BlueRicky extends Mino {
    public BlueRicky() {
        create(Color.BLUE);
    }

    @Override
    public void getRotation0Degrees() {
        // Standard backwards L
        //   o
        //   o
        // o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[2]);
        updateXY(MinoRotation.ZERO);
    }

    @Override
    public void getRotation90Degrees() {
        // L on its back
        //  o
        //  o o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveRightOf(tmpB[0]);
        tmpB[2].moveLeftOf(tmpB[0]);
        tmpB[3].moveAbove(tmpB[2]);
        updateXY(MinoRotation.NINETY);
    }

    @Override
    public void getRotation180Degrees() {
        // Standard backwards L flipped vertically
        //   o o
        //   o
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[2]);
        updateXY(MinoRotation.ONEEIGHTY);
    }

    @Override
    public void getRotation270Degrees() {
        // Standard backwards L on its tiptoe
        //
        //   o 0 o
        //       o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[2]);
        updateXY(MinoRotation.TWOSEVENTY);
    }

    public void setXY(int x, int y) {
        //   o <-- b[1]
        //   o <-- b[0] is our center point
        // o o <-- b[2 and 3]
        b[0].moveOnTo(x, y);
        b[1].moveAbove(b[0]);
        b[2].moveBelow(b[0]);
        b[3].moveLeftOf(b[2]);
        directionToRotate = MinoRotation.ZERO;
    }
}
