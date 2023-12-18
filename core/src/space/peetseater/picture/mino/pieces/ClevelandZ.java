package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the left facing Z
public class ClevelandZ extends Mino{
    public ClevelandZ() {
        create(Color.RED);
    }


    @Override
    public void setXY(int x, int y) {
        // o o
        //   b o
        b[0].moveOnTo(x, y);
        b[1].moveAbove(b[0]);
        b[2].moveRightOf(b[0]);
        b[3].moveLeftOf(b[1]);
    }

    @Override
    public void getRotation0Degrees() {
        // o o
        //   b o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[1]);
        updateXY(MinoRotation.ZERO);
    }

    @Override
    public void getRotation90Degrees() {
        //     o
        //   b o
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveRightOf(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveAbove(tmpB[1]);
        updateXY(MinoRotation.NINETY);
    }

    @Override
    public void getRotation180Degrees() {
        //
        // o b
        //   o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveLeftOf(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[1]);
        updateXY(MinoRotation.ONEEIGHTY);
    }

    @Override
    public void getRotation270Degrees() {
        //     o
        //   o b
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveBelow(tmpB[1]);
       updateXY(MinoRotation.TWOSEVENTY);
    }
}
