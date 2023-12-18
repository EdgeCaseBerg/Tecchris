package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA: The Right L
public class OrangeRicky extends Mino {

    public OrangeRicky() {
        create(Color.ORANGE);
    }

    @Override
    public void setXY(int x, int y) {
        // This is like the blue ricky, but flipped.
        // o
        // o     <--- this is b[0]
        // o o
        b[0].moveOnTo(x, y);
        b[1].moveAbove(b[0]);
        b[2].moveBelow(b[0]);
        b[3].moveRightOf(b[2]);
    }

    @Override
    public void getRotation0Degrees() {
        // o
        // o     <--- this is b[0]
        // o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(b[0]);
        tmpB[2].moveBelow(b[0]);
        tmpB[3].moveRightOf(tmpB[2]);
        updateXY(MinoRotation.ZERO);
    }

    @Override
    public void getRotation90Degrees() {
        //
        // o b o
        // o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[1]);
        updateXY(MinoRotation.NINETY);
    }

    @Override
    public void getRotation180Degrees() {
        // o o
        //   b
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[2]);
        updateXY(MinoRotation.ONEEIGHTY);
    }

    @Override
    public void getRotation270Degrees() {
        //     o
        // o b o
        //
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveAbove(tmpB[2]);
       updateXY(MinoRotation.TWOSEVENTY);
    }
}
