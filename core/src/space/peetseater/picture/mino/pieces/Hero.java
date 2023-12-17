package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.graphics.Color;

// AKA the tallboy line
public class Hero extends Mino {

    public Hero() {
        create(Color.CYAN);
    }

    @Override
    public void setXY(int x, int y) {
        // Start out horizontally long
        // o b o o
        b[0].moveOnTo(x, y);
        b[1].moveLeftOf(b[0]);
        b[2].moveRightOf(b[0]);
        b[3].moveRightOf(b[2]);
    }

    @Override
    public void getDirection1() {
        // o b o o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveLeftOf(tmpB[0]);
        tmpB[2].moveRightOf(tmpB[0]);
        tmpB[3].moveRightOf(tmpB[2]);
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // o
        // b
        // o
        // o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveAbove(tmpB[0]);
        tmpB[2].moveBelow(tmpB[0]);
        tmpB[3].moveBelow(tmpB[2]);
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        // Note: tutorial notes that you can re-use the other bar for this,
        // but, that's not technically true since o o b o and o b o o are
        // different.
        // o o b o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveRightOf(tmpB[0]);
        tmpB[2].moveLeftOf(tmpB[0]);
        tmpB[3].moveLeftOf(tmpB[2]);
        updateXY(3);
    }

    @Override
    public void getDirection4() {
        // Note, tutorial says to re-use state 1, but its not true from the centerpoints
        // perspective on the rotation, so I implemented it anyway
        // o
        // o
        // b
        // o
        tmpB[0].moveOnTo(b[0]);
        tmpB[1].moveBelow(tmpB[0]);
        tmpB[2].moveAbove(tmpB[0]);
        tmpB[3].moveAbove(tmpB[2]);
        updateXY(4);
    }
}
