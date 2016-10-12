package in.ishankhanna.tinglingsquares;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author Ishan Khanna
 */

public class TinglingSquaresView extends FrameLayout {

    public static int ANIMATION_TIME_BASE = 500;
    public static float LAG_FACTOR = 0.5f;

    public static final float THRESHOLD_TO_TRIGGER_NEXT_ANIMATION = 0.7f;
    SquareView[][] squareViews = new SquareView[3][4];

    private Context ctx;

    private int side = 40;
    private int padding = 8;
    private int w,h;
    private boolean moveFromLeftToRight = true;
    private PropertyValuesHolder pvhLeftToRightAnimation;
    private PropertyValuesHolder pvhRightToLeftAnimation;

    private ObjectAnimator[] animatorsLeftToRight = new ObjectAnimator[12];
    private ObjectAnimator[] animatorsRightToLeft = new ObjectAnimator[12];

    private AnimatorSet column1AnimationLTR, column2AnimationLTR, column3AnimationLTR, column4AnimationLTR;

    private AnimatorSet column1AnimationRTL, column2AnimationRTL, column3AnimationRTL, column4AnimationRTL;

    private boolean shouldAnimate = true;

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

    public void init() {
        for (int m=0;m<3;m++) {
            for (int n=0;n<4;n++) {
                squareViews[m][n] = new SquareView(ctx, m, n);
            }
        }

        initAnimations();
    }

    public void initAnimations() {
        pvhLeftToRightAnimation = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);
        pvhRightToLeftAnimation = PropertyValuesHolder.ofFloat(View.ROTATION, 90, 0);

        int m=0,n=0;
        for (int i=0;i<12; i++) {
            animatorsLeftToRight[i] = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n], pvhLeftToRightAnimation);
            switch (m) {
                case 0:
                    animatorsLeftToRight[i].setDuration(ANIMATION_TIME_BASE);
                    break;
                case 1:
                    animatorsLeftToRight[i].setDuration((int) (ANIMATION_TIME_BASE * LAG_FACTOR));
                    break;
                case 2:
                    animatorsLeftToRight[i].setDuration(ANIMATION_TIME_BASE);
                    break;
            }
            n++;
            if (n > 3) {
                m++;
                n = 0;
            }
        }

        m=0;n=0;

        for (int i=0;i<12;i++) {
            animatorsRightToLeft[i] = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n], pvhRightToLeftAnimation);
            switch (m) {
                case 0:
                    animatorsRightToLeft[i].setDuration(ANIMATION_TIME_BASE);
                    break;
                case 1:
                    animatorsRightToLeft[i].setDuration(ANIMATION_TIME_BASE);
                    break;
                case 2:
                    animatorsRightToLeft[i].setDuration((int)(ANIMATION_TIME_BASE * LAG_FACTOR));
                    break;
            }
            n++;
            if (n > 3) {
                m++;
                n = 0;
            }
        }

        column1AnimationLTR = new AnimatorSet();
        column1AnimationLTR.playTogether(animatorsLeftToRight[0],animatorsLeftToRight[4], animatorsLeftToRight[8]);

        column2AnimationLTR = new AnimatorSet();
        column2AnimationLTR.playTogether(animatorsLeftToRight[1],animatorsLeftToRight[5], animatorsLeftToRight[9]);
        column2AnimationLTR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column1AnimationLTR.start();
            }
        });

        column3AnimationLTR = new AnimatorSet();
        column3AnimationLTR.playTogether(animatorsLeftToRight[2],animatorsLeftToRight[6], animatorsLeftToRight[10]);
        column3AnimationLTR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column2AnimationLTR.start();
            }
        });


        column4AnimationLTR = new AnimatorSet();
        column4AnimationLTR.playTogether(animatorsLeftToRight[3],animatorsLeftToRight[7], animatorsLeftToRight[11]);
        column4AnimationLTR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column3AnimationLTR.start();
            }
        });

        column4AnimationRTL = new AnimatorSet();
        column4AnimationRTL.playTogether(animatorsRightToLeft[3], animatorsRightToLeft[7], animatorsRightToLeft[11]);

        column3AnimationRTL = new AnimatorSet();
        column3AnimationRTL.playTogether(animatorsRightToLeft[2], animatorsRightToLeft[6], animatorsRightToLeft[10]);
        column3AnimationRTL.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column4AnimationRTL.start();
            }
        });

        column2AnimationRTL = new AnimatorSet();
        column2AnimationRTL.playTogether(animatorsRightToLeft[1], animatorsRightToLeft[5], animatorsRightToLeft[9]);
        column2AnimationRTL.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column3AnimationRTL.start();
            }
        });

        column1AnimationRTL = new AnimatorSet();
        column1AnimationRTL.playTogether(animatorsRightToLeft[0], animatorsRightToLeft[4], animatorsRightToLeft[8]);
        column1AnimationRTL.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                column2AnimationRTL.start();
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

    public void runAnimation(long delay) {
        if (moveFromLeftToRight) {
            moveFromLeftToRight = false;
            postDelayed(animationRunnableLeftToRight,delay);
        } else {
            moveFromLeftToRight = true;
            postDelayed(animationRunnableRightToLeft,delay);
        }
    }

    private Runnable animationRunnableLeftToRight = new Runnable() {
        @Override
        public void run() {
            column4AnimationLTR.start();
        }
    };

    private Runnable animationRunnableRightToLeft = new Runnable() {
        @Override
        public void run() {
            column1AnimationRTL.start();
        }
    };

    public static int getAnimationTimeBase() {
        return ANIMATION_TIME_BASE;
    }

    public static void setAnimationTimeBase(int animationTimeBase) {
        ANIMATION_TIME_BASE = animationTimeBase;
    }

    public static float getLagFactor() {
        return LAG_FACTOR;
    }

    public static void setLagFactor(float lagFactor) {
        LAG_FACTOR = lagFactor;
    }


}
