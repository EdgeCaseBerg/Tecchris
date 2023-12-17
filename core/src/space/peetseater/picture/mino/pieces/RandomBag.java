package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** One of each of the minos are dealt until we run out, then it's reshuffled.
 *
 * */
public class RandomBag {
    private Stack<Mino> bag;

    public RandomBag() {
        bag = new Stack<>();
        fillNewBag();
    }

    private void fillNewBag() {
        List<Mino> pieces = new ArrayList<>(7);
        pieces.add(new BlueRicky());
        pieces.add(new ClevelandZ());
        pieces.add(new OrangeRicky());
        pieces.add(new Hero());
        pieces.add(new RhodeIsland());
        pieces.add(new Smashboy());
        pieces.add(new Teewee());
        while (!pieces.isEmpty()) {
            int idx = MathUtils.random(0, pieces.size() - 1);
            Mino toAdd = pieces.remove(idx);
            bag.push(toAdd);
        }
    }

    public Mino getNextPiece() {
        if (bag.empty()) {
            fillNewBag();
        }
        return bag.pop();
    }
}
