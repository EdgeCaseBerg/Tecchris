package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// The box
public class Smashboy extends Mino {

    public Smashboy() {
        create(Color.YELLOW);
    }


    @Override
    public void setXY(int x, int y) {
        // b o
        // o o
        b[0].moveOnTo(x, y);
        b[1].moveRightOf(b[0]);
        b[2].moveBelow(b[0]);
        b[3].moveRightOf(b[2]);
    }

    @Override
    public void getRotation0Degrees() {}
    @Override
    public void getRotation90Degrees() {}
    @Override
    public void getRotation180Degrees() {}
    @Override
    public void getRotation270Degrees() {}
}
