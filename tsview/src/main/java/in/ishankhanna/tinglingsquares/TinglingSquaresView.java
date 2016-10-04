package in.ishankhanna.tinglingsquares;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Ishan Khanna
 */

public class TinglingSquaresView extends FrameLayout {

    SquareView[] squareViews = new SquareView[9];

    private Context ctx;

    private int side = 10;
    private int padding = 2;
    private int w,h;

    public TinglingSquaresView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init();
    }

    public TinglingSquaresView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        init();
    }

    private void init() {

    }

    private void initSquares(int w, int h) {

        float cX, cY;

        // MIDDLE LAYER

        // CENTER
        cX = w/2;
        cY = h/2;
        squareViews[4] = new SquareView(ctx, cX, cY);

        // LEFT
        cX = w/2 - side - padding;
        cY = h/2;
        squareViews[3] = new SquareView(ctx, cX, cY);

        // RIGHT
        cX = w/2 + side + padding;
        cY = h/2;

        squareViews[5] = new SquareView(ctx, cX, cY);

        // TOP LAYER

        //CENTER
        cX = w/2;
        cY = h/2 - side - padding;

        squareViews[1] = new SquareView(ctx, cX, cY);

        //LEFT
        cX = w/2 - side - padding;
        cY = h/2 - side - padding;

        squareViews[0] = new SquareView(ctx, cX, cY);

        //RIGHT
        cX = w/2 + side + padding;
        cY = h/2 - side - padding;

        squareViews[2] = new SquareView(ctx, cX, cY);

        // BOTTOM LAYER
        //CENTER
        cX = w/2;
        cY = h/2 + side + padding;

        squareViews[7] = new SquareView(ctx, cX, cY);

        //LEFT
        cX = w/2 - side - padding;
        cY = h/2 + side + padding;

        squareViews[6] = new SquareView(ctx, cX, cY);

        //RIGHT
        cX = w/2 + side + padding;
        cY = h/2 + side + padding;

        squareViews[8] = new SquareView(ctx, cX, cY);

        for (int i=0;i<9;i++) {
            addView(squareViews[i]);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        initSquares(w, h);
    }


}
