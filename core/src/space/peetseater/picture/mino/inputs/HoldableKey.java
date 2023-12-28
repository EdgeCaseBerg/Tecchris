package space.peetseater.picture.mino.inputs;

class HoldableKey {
    public boolean pressed = false;
    public float secondsHeld = 0.0f;
    public boolean beginCounting = false;

    public HoldableKey() {}
    public void update(float seconds) {
        if (beginCounting) {
            secondsHeld += seconds;
        }
    }
    public void reEnableAfter(float threshold) {
        if (secondsHeld >= threshold) {
            pressed = true;
            secondsHeld = 0.0f;
        }
    }

    public void reset() {
        pressed = false;
        secondsHeld = 0.0f;
        beginCounting = false;
    }
}
