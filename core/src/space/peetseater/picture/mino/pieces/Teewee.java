package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// The T piece for t spinning joy
public class Teewee extends Mino {

    public Teewee() {
        create(Color.MAGENTA);
    }

    @Override
    public void setXY(int x, int y) {
        //   o
        // o b o
        b[0].moveOnTo(x, y);
        b[1].moveLeftOf(b[0]);
        b[2].moveRightOf(b[0]);
        b[3].moveAbove(b[0]);
        directionToRotate = MinoRotation.ZERO;
    }

    @Override
    public void getRotation0Degrees() {
        //   o
        // o b o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveAbove(tmpB[0]);
        updateXY(MinoRotation.ZERO);
    }

    @Override
    public void getRotation90Degrees() {
        // o
        // b o
        // o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[0]);
        updateXY(MinoRotation.NINETY);
    }

    @Override
    public void getRotation180Degrees() {
        // o b o
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[0]);
        updateXY(MinoRotation.ONEEIGHTY);
    }

    @Override
    public void getRotation270Degrees() {
        //   o
        // o b
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[0]);
        updateXY(MinoRotation.TWOSEVENTY);
    }


}
