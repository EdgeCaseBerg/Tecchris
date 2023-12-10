package space.peetseater.lewd.mino.pieces;

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
        directionToRotate = 1;
    }

    @Override
    public void getDirection1() {
        //   o
        // o b o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveAbove(tmpB[0]);
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // o
        // b o
        // o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[0]);
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        // o b o
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[0]);
        updateXY(3);
    }

    @Override
    public void getDirection4() {
        //   o
        // o b
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[0]);
        updateXY(4);
    }


}
