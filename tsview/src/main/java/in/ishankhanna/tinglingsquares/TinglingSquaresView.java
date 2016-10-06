package in.ishankhanna.tinglingsquares;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
    private boolean moveFromLeftToRight = true;
    private PropertyValuesHolder propertyValuesHolder;

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
        int w = (side * 5) + (padding * 4) + (side * 2);
        int h = (side * 3) + (padding * 2) + (side * 2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
        setLayoutParams(layoutParams);
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

    private Runnable col1Runnable = new Runnable() {
        @Override
        public void run() {
            AnimatorSet animatorSet = new AnimatorSet();
            List<Animator> animators = new ArrayList<>();
            for (int row=0;row<3;row++) {
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(squareViews[row][0], propertyValuesHolder);
                switch (row) {
                    case 0:
                        animator.setDuration(800);
                        break;
                    case 1:
                        animator.setDuration(700);
                        break;
                    case 2:
                        animator.setDuration(600);
                        break;
                }
                animators.add(animator);
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    };

    private Runnable col2Runnable = new Runnable() {
        @Override
        public void run() {
            AnimatorSet animatorSet = new AnimatorSet();
            List<Animator> animators = new ArrayList<>();
            for (int row=0;row<3;row++) {
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(squareViews[row][1], propertyValuesHolder);
                switch (row) {
                    case 0:
                        animator.setDuration(800);
                        break;
                    case 1:
                        animator.setDuration(700);
                        break;
                    case 2:
                        animator.setDuration(600);
                        break;
                }
                animators.add(animator);
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    };

    private Runnable col3Runnable = new Runnable() {
        @Override
        public void run() {
            AnimatorSet animatorSet = new AnimatorSet();
            List<Animator> animators = new ArrayList<>();
            for (int row=0;row<3;row++) {
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(squareViews[row][2], propertyValuesHolder);
                switch (row) {
                    case 0:
                        animator.setDuration(800);
                        break;
                    case 1:
                        animator.setDuration(700);
                        break;
                    case 2:
                        animator.setDuration(600);
                        break;
                }
                animators.add(animator);
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    };

    private Runnable col4Runnable = new Runnable() {
        @Override
        public void run() {
            AnimatorSet animatorSet = new AnimatorSet();
            List<Animator> animators = new ArrayList<>();
            for (int row=0;row<3;row++) {
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(squareViews[row][3], propertyValuesHolder);
                switch (row) {
                    case 0:
                        animator.setDuration(800);
                        break;
                    case 1:
                        animator.setDuration(700);
                        break;
                    case 2:
                        animator.setDuration(600);
                        break;
                }
                animators.add(animator);
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    };

    public void runAnimation() {
        if (moveFromLeftToRight) {
            propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);
            moveFromLeftToRight = false;
        } else {
            propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 90, 0);
            moveFromLeftToRight = true;
        }
        postDelayed(col1Runnable, 800);
        postDelayed(col2Runnable, 900);
        postDelayed(col3Runnable, 1000);
        postDelayed(col4Runnable, 1100);
    }
}
