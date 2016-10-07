package in.ishankhanna.tinglingsquares;

import android.view.animation.Interpolator;

/**
 * @author Ishan Khanna
 */

public class TumbleInterpolator implements Interpolator {

    public TumbleInterpolator() {
    }

    @Override
    public float getInterpolation(float t) {
        return t * t * ((8 * t) - 3);
    }
}
