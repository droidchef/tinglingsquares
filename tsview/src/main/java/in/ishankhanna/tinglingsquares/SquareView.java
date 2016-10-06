package in.ishankhanna.tinglingsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * @author Ishan Khanna
 */

public class SquareView extends View {

    /**
     * Side of the square.
     */
    private int s;

    /**
     * Padding between two adjacent squares.
     */
    private int p;

    private Paint paint;
    private float left,top,right,bottom;
    private final int row;
    private final int col;

    public SquareView(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
        init();
    }

    private void init() {
        s = 40;
        p = 8;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        top = (row * s) + (row * p);
        left = (col * s) + (col * p);
        bottom = top + s;
        right = left + s;
        setPivot();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void setPivot() {
        float pX, pY;
        // Center
//        pX = (right - left) / 2;
//        pY = (bottom - top) / 2;
        // Bottom Right
        pX = right;
        pY = bottom;
        setPivotX(pX);
        setPivotY(pY);
    }
}
