package space.peetseater.picture.mino.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class GhostPiece extends Mino {

    private final Mino ghostOfPiece;

    public GhostPiece(Mino ghostOf) {
        ghostOfPiece = ghostOf;
        create(Color.LIGHT_GRAY);
        updateBlocks();
    }

    public void updateBlocks() {
        for (int i = 0; i < b.length; i++) {
            b[i].x = ghostOfPiece.b[i].x;
            b[i].y = ghostOfPiece.b[i].y;
        }
    }

    @Override
    public void setXY(int x, int y) {
        for (int i = 0; i < b.length; i++) {
            b[i].x = ghostOfPiece.b[i].x;
            b[i].y = ghostOfPiece.b[i].y;
        }
        for (int i = 0; i < b.length; i++) {
            tmpB[i].x = ghostOfPiece.b[i].x;
            tmpB[i].y = ghostOfPiece.b[i].y;
        }
    }

    private void setTmpB() {
        for (int i = 0; i < b.length; i++) {
            tmpB[i].x = ghostOfPiece.b[i].x;
            tmpB[i].y = ghostOfPiece.b[i].y;
        }
    }

    @Override
    public void getRotation0Degrees() {
        setTmpB();
    }

    @Override
    public void getRotation90Degrees() {
        setTmpB();
    }

    @Override
    public void getRotation180Degrees() {
        setTmpB();
    }

    @Override
    public void getRotation270Degrees() {
        setTmpB();
    }

    public void moveToCollision(Array<Block> staticBlocks, int playAreaBottomY) {
        Gdx.app.log("GHOST", ""+playAreaBottomY);
        updateBlocks();
        // If the piece is already at the bottom of the area, then do nothing.
        HashMap<Integer, Integer> ghostXtoLowestY = new HashMap<>(b.length);
        HashMap<Integer, Integer> moveTo = new HashMap<>(b.length);
        int moveDownUntilY = playAreaBottomY;
        int closestYToGround = b[0].y;
        for(Block block : b) {
            // We add in the movement down so we automatically check collisions below the piece
            closestYToGround = Math.min(closestYToGround, block.y);
            Integer possibleSharedYsForX = ghostXtoLowestY.getOrDefault(block.x, 1000);
            ghostXtoLowestY.put(block.x, Math.min(possibleSharedYsForX, block.y));
        }
        Gdx.app.log("GHOSTXTOY", ghostXtoLowestY.toString());
        // Check for collisions with the static blocks
        for (Block block : staticBlocks) {
            Gdx.app.log("GHOSTCHECK", ""+block.x);
            Integer collisionY = ghostXtoLowestY.get(block.x);
            if (collisionY == null) {
                continue;
            }
            Gdx.app.log("GHOSTCHECK", "collision " + block.y + " /cy:" + collisionY);

            int yAboveThisBlock = block.y + Block.SIZE;
            if (moveDownUntilY < yAboveThisBlock) {
                Gdx.app.log("GHOSTSET", "" + yAboveThisBlock);
                moveDownUntilY = yAboveThisBlock;
            }
            if (moveTo.containsKey(block.x)) {
                int alreadyDetectedCollisionPoint = moveTo.get(block.x);
                yAboveThisBlock = Math.max(alreadyDetectedCollisionPoint, yAboveThisBlock);
            }
            moveTo.put(block.x, yAboveThisBlock);

        }

        Gdx.app.log("GHOSTMOVETO", moveTo.toString());

        if (moveTo.isEmpty()) {
            // No collision with any static blocks,
            // move the piece down by however many spaces
            // until its resting on the bottom.
            int timesToMove = (closestYToGround - playAreaBottomY) / Block.SIZE;
            Gdx.app.log("1. MOVE BY",  "" + timesToMove);
            for (int i = 0; i < timesToMove; i++) {
                for (Block block : b) {
                    block.y -= Block.SIZE;
                }
            }
        } else {
            // One of our pieces collide with a static block
            // So we need to move down until the collision spot
            // would be reached.
            int y = Block.SIZE;
            int dy = 1000;
            int furtherBlockDownY = 0;
            Iterator<Map.Entry<Integer, Integer>> collisions = moveTo.entrySet().iterator();
            while(collisions.hasNext()) {
                Map.Entry<Integer, Integer> xyCollision = collisions.next();
                for (Block block : b) {
                    Integer lowestY = ghostXtoLowestY.get(block.x);
                    if (block.x == xyCollision.getKey() && block.y == lowestY) {
                         dy = Math.min(Math.abs(block.y - xyCollision.getValue()), dy);
                    }
                }
            }
            Gdx.app.log("GHOSTdy", "" + dy);


            int timesToMove = dy / Block.SIZE;
            Gdx.app.log("2. MOVE BY",  "" + timesToMove + " because y is " + y + " and closestY is " + closestYToGround);
            for (int i = 0; i < timesToMove; i++) {
                for (Block block : b) {
                    if (block.y == playAreaBottomY) {
                        return;
                    }
                }
                for (Block block : b) {
                    block.y -= Block.SIZE;
                }
            }
        }
    }
}
