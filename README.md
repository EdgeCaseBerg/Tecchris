## Tecchris

Heavily based on youtube tutorial by RyiSnow. 
Watch him make Tetris in Swing here:

    https://www.youtube.com/watch?v=N1ktYfszqnM

This is a version that I ported to LibGDX 1.12.0.

Up to commit d894d6a60113c8b93c89f42b7dd77e5b2a302884 is following
the tutorial fairly explicitly with only a few minor tweaks to make
my hair not stand on end or to make my job easier as I followed along.

Post that commit is where customizations and optimizations start to
appear, and that may be a topic of discussion in the future or have
notes here in the readme for points of reference.

## Tweaks from the tutorial

### Block setup

RyiSnow, being a performant coder probably used to c++ and the world
of data locality, does things like this:

    // draw the blue L like:
    // [1]
    // [0]
    // [2] [3]
    b[0].x = x;
    b[0].y = y;
    b[1].x = b[0].x;
    b[1].y = b[0].y - Block.SIZE;
    b[2].x = b[0].x;
    b[2].y = b[0].y + Block.SIZE;
    b[3].x = b[0].x + Block.SIZE;
    b[3].y = b[0].y + Block.SIZE;

This is _very_ error prone and easy to mess up. As he does in the video
when making the Mino_L2 class at 38:01, and then he doesn't catch it 
until 45:53. 
    
I opted for a different approach that prevented me from making the same 
mistake as he did. Contrast the above code with the following:

    b[0].moveOnTo(x, y);
    b[1].moveAbove(b[0]);
    b[2].moveBelow(b[0]);
    b[3].moveRightOf(b[2]);

Besides the moveOnTo which I could probably name moveTo (It's an 
overriden method that can take x,y or another block, hence its name) you can
read this pretty easily, understand what shape its making, and you can't
mess up typing x or y. It might be a virtual method hop away if this was 
c++ code, but we're in Java, and I'll be damned if I'm not going to avoid
making bugs for myself.

### Piece Rotation

In the tutorial for the Square, both Z pieces, and the bar, RyiSnow opts
to not implement the directional methods for some values because the piece's
shape itself doesn't change. 

While my code follows this approach for the Square for the duration of the
tutorial, I don't agree that the rotation is the same. For example, if we're
taking `b[0]` as our centerpoint and rotating the other blocks around it,
then the there _are_ differences, especially when you consider that the hero
piece will be further in a direction depending on which rotation it was.

So, my code implements all 4 directional functions so that the collision
detection code accurate prevents a user from rotating a hero to the left if
the wall is in the way since they're only 2 spaces to it and not 3. I'm not
sure if the original tetris game does this or if RyiSnow's is more accurate,
but I'd rather have mine follow through on this anchor rotation.

### Orientation

One major tweak is that libgdx and swing's x,y directions are anchored in
different places. For the tutorial, most code he has increments the position
of the y value to move pieces. For mine, since its using default LibGDX, 
has to flip the y vertex on its head. So some math is a bit different.

### Guarding from overflow

I don't think it would be an issue if one of the accumulator values
happened to overflow after a piece was moved into the static block
collection, but I don't want to find out either. So, my code stops
updating any sort of accumulator for slide time, effects, or whatever
after a Mino piece has become inactive.

### Hold vs press button movement

I found that since we're not implementing frame perfect rendering
like the Swing stuff is where RyiSnow is literally counting frames
for how long something lasts, I'm not using seconds from the delta
for libgdx. That, combined with the Key inputs, meant things got
a lot harder to move left and right precisely, so in my version of the
game, you have to press left 3 times to move left 3 times, you can't
just hold it down.

### Music

I did use the sound effects from the tutorial (Great stuff RyiSnow!)
but I didn't want to use the background music he did. While the tetris
theme is copyrighted, the actual folk song is not. Which means if you
use a version of it that's been transcribed from the sheet music and 
arranged either by yourself or someone else who made it freely available.
You're home free :) 

So, my game uses Korobeiniki made in BeepBox. According to the internet
that should be fine copyright wise, and also, it's not like I'm selling
this game. It's a learning and educational thing we're doing here.