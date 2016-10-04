package in.ishankhanna.tinglingsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Ishan Khanna
 */

public class SquareView extends View {

    private int side;
    private Paint paint;
    private float cX,cY;
    private float left,top,right,bottom;

    public SquareView(Context context, float cX, float cY) {
        super(context);
        this.cX = cX;
        this.cY = cY;
        setPivotX(cX);
        setPivotY(cY);
        init();
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        side = 10;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        int hs = side / 2;

        left = cX - hs;
        top = cY - hs;
        right = cX + hs;
        bottom = cY + hs;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
