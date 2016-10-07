package in.ishankhanna.tinglingsquares;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author Ishan Khanna
 */

public class TinglingSquaresView extends FrameLayout {

    public static final int LEFT_TO_RIGHT_ANIM_TIME = 400;
    public static final float THRESHOLD_TO_TRIGGER_NEXT_ANIMATION = 0.7f;
    SquareView[][] squareViews = new SquareView[3][4];

    private Context ctx;

    private int side = 40;
    private int padding = 8;
    private int w,h;
    private boolean moveFromLeftToRight = true;
    private PropertyValuesHolder propertyValuesHolder;

    private ObjectAnimator square1, square2, square3, square4, square5, square6, square7, square8,
            square9, square10, square11, square12;

    private AnimatorSet column1Animation,column2Animation,column3Animation,column4Animation;

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

        propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);

        square1 = ObjectAnimator.ofPropertyValuesHolder(squareViews[0][0], propertyValuesHolder);
        square2 = ObjectAnimator.ofPropertyValuesHolder(squareViews[0][1], propertyValuesHolder);
        square3 = ObjectAnimator.ofPropertyValuesHolder(squareViews[0][2], propertyValuesHolder);
        square4 = ObjectAnimator.ofPropertyValuesHolder(squareViews[0][3], propertyValuesHolder);
//        square1.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 200);
//        square2.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 150);
//        square3.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 50);
//        square4.setDuration(LEFT_TO_RIGHT_ANIM_TIME);

        square5 = ObjectAnimator.ofPropertyValuesHolder(squareViews[1][0], propertyValuesHolder);
        square6 = ObjectAnimator.ofPropertyValuesHolder(squareViews[1][1], propertyValuesHolder);
        square7 = ObjectAnimator.ofPropertyValuesHolder(squareViews[1][2], propertyValuesHolder);
        square8 = ObjectAnimator.ofPropertyValuesHolder(squareViews[1][3], propertyValuesHolder);
//        square5.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 200);
//        square6.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 150);
//        square7.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 100);
//        square8.setDuration(LEFT_TO_RIGHT_ANIM_TIME);

        square9 = ObjectAnimator.ofPropertyValuesHolder(squareViews[2][0], propertyValuesHolder);
        square10 = ObjectAnimator.ofPropertyValuesHolder(squareViews[2][1], propertyValuesHolder);
        square11 = ObjectAnimator.ofPropertyValuesHolder(squareViews[2][2], propertyValuesHolder);
        square12 = ObjectAnimator.ofPropertyValuesHolder(squareViews[2][3], propertyValuesHolder);
//        square10.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 150);
//        square11.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 50);
//        square9.setDuration(LEFT_TO_RIGHT_ANIM_TIME - 200);
//        square12.setDuration(LEFT_TO_RIGHT_ANIM_TIME);

        column4Animation = new AnimatorSet();
        column4Animation.play(square4).with(square12);

        column3Animation = new AnimatorSet();
        column3Animation.play(square3).with(square11);

        column2Animation = new AnimatorSet();
        column2Animation.play(square2).with(square10);

        column1Animation = new AnimatorSet();
        column1Animation.play(square1).with(square9);

        column1Animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setupAnimationTriggerListeners();
            }
        });

        setupAnimationTriggerListeners();

    }

    private void setupAnimationTriggerListeners() {
        square8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column4Animation.start();
                    square8.removeAllUpdateListeners();
                }

            }
        });


        square7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column3Animation.start();
                    square7.removeAllUpdateListeners();
                }

            }
        });

        square6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column2Animation.start();
                    square6.removeAllUpdateListeners();
                }

            }
        });

        square5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column1Animation.start();
                    square5.removeAllUpdateListeners();
                }

            }
        });

        square4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    square7.start();
                    square4.removeAllUpdateListeners();
                }
            }
        });

        square3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    square6.start();
                    square3.removeAllUpdateListeners();
                }
            }
        });

        square2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    square5.start();
                    square2.removeAllUpdateListeners();
                }
            }
        });
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

    public void runAnimation() {
        if (moveFromLeftToRight) {
            propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);
            moveFromLeftToRight = false;
            post(animationRunnable);
        } else {
            propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 90, 0);
            moveFromLeftToRight = true;
            post(animationRunnable);
        }
    }

    private Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            square8.start();
        }
    };

}
