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
    public void getDirection1() {}
    @Override
    public void getDirection2() {}
    @Override
    public void getDirection3() {}
    @Override
    public void getDirection4() {}
}
