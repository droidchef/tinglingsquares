package in.ishankhanna.tinglingsquares;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ishan Khanna
 */

public class TinglingSquaresView extends FrameLayout {

    SquareView[][] squareViews = new SquareView[3][4];

    private Context ctx;

    private int side = 40;
    private int padding = 8;
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int w = (side * 5) + (padding * 4);
        int h = (side * 3) + (padding * 2);
        setLayoutParams(new RelativeLayout.LayoutParams(w, h));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void init() {
        for (int m=0;m<3;m++) {
            for (int n=0;n<4;n++) {
                squareViews[m][n] = new SquareView(ctx, m, n);
            }
        }

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);

        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (int m=2;m>=0;m--) {
            for (int n=3;n>=0;n--) {
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n], propertyValuesHolder);
                animator.setDuration(800);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animators.add(animator);
            }
        }

        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        for (int m=0;m<3;m++) {
            for (int n=0;n<4;n++) {
                addView(squareViews[m][n]);
            }
        }
    }



}
