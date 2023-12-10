package space.peetseater.lewd.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA The right facing Z
public class RhodeIsland extends Mino {
    public RhodeIsland() {
        create(Color.GREEN);
    }

    @Override
    public void setXY(int x, int y) {
        //   o o
        // o b
        b[0].moveOnTo(x, y);
        b[1].moveLeftOf(b[0]);
        b[2].moveAbove(b[0]);
        b[3].moveRightOf(b[2]);
    }

    @Override
    public void getDirection1() {
        //   o o
        // o b
        //
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[2]);
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        //   o
        //   b o
        //     o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveBelow(tmpB[2]);
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        //
        //   b o
        // o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveRightOf(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[2]);
        updateXY(3);
    }

    @Override
    public void getDirection4() {
        // o
        // o b
        //   o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveLeftOf(tmpB[0]);
        tmpB[3].moveAbove(tmpB[2]);
        updateXY(4);
    }
}
