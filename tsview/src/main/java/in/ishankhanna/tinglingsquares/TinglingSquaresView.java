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

    private static int ANIMATION_TIME_BASE = 500;

    private static float LAG_FACTOR = 0.5f;

    private static final long ANIMATION_RESTART_DELAY = 150;

    private final SquareView[][] squareViews = new SquareView[3][4];

    private Context ctx;

    private int side = 40;

    private int padding = 8;

    private boolean moveFromLeftToRight = true;

    private final ObjectAnimator[] animatorsLeftToRight = new ObjectAnimator[12];
    private final ObjectAnimator[] animatorsRightToLeft = new ObjectAnimator[12];

    private AnimatorSet column1AnimationLTR, column2AnimationLTR, column3AnimationLTR, column4AnimationLTR;

    private AnimatorSet column1AnimationRTL, column2AnimationRTL, column3AnimationRTL, column4AnimationRTL;

    private final AnimatorSet scene1 = new AnimatorSet();
    private final AnimatorSet scene2 = new AnimatorSet();

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

        setupLeftToRightAnimators();
        setupRightToLeftAnimators();

        scene1.playTogether(column1AnimationRTL, column2AnimationRTL, column3AnimationRTL, column4AnimationRTL);
        scene2.playTogether(column4AnimationLTR, column2AnimationLTR, column3AnimationLTR, column1AnimationLTR);
    }

    private void setupLeftToRightAnimators() {
        PropertyValuesHolder pvhLeftToRightAnimation = PropertyValuesHolder.ofFloat(View
                .ROTATION, 0, 90);

        int m=0,n=0;

        for (int i=0;i<12; i++) {
            animatorsLeftToRight[i] = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n],
                    pvhLeftToRightAnimation);
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

        column4AnimationLTR = new AnimatorSet();
        column4AnimationLTR.playTogether(animatorsLeftToRight[3],animatorsLeftToRight[7], animatorsLeftToRight[11]);

        column3AnimationLTR = new AnimatorSet();
        column3AnimationLTR.playTogether(animatorsLeftToRight[2],animatorsLeftToRight[6], animatorsLeftToRight[10]);
        column3AnimationLTR.setStartDelay(getStartDelayForColumn(3, true));

        column2AnimationLTR = new AnimatorSet();
        column2AnimationLTR.playTogether(animatorsLeftToRight[1],animatorsLeftToRight[5], animatorsLeftToRight[9]);
        column2AnimationLTR.setStartDelay(getStartDelayForColumn(2, true));

        column1AnimationLTR = new AnimatorSet();
        column1AnimationLTR.playTogether(animatorsLeftToRight[0],animatorsLeftToRight[4], animatorsLeftToRight[8]);
        column1AnimationLTR.setStartDelay(getStartDelayForColumn(1, true));
        column1AnimationLTR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                runAnimation(ANIMATION_RESTART_DELAY);
            }
        });

    }

    private void setupRightToLeftAnimators() {
        PropertyValuesHolder pvhRightToLeftAnimation = PropertyValuesHolder.ofFloat(View
                .ROTATION, 90, 0);

        int m=0,n=0;
        for (int i=0;i<12;i++) {
            animatorsRightToLeft[i] = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n],
                    pvhRightToLeftAnimation);
            switch (m) {
                case 0:
                    animatorsRightToLeft[i].setDuration(ANIMATION_TIME_BASE);
                    break;
                case 1:
                    animatorsRightToLeft[i].setDuration((int)(ANIMATION_TIME_BASE * (LAG_FACTOR + 0.25f)));
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

        column4AnimationRTL = new AnimatorSet();
        column4AnimationRTL.playTogether(animatorsRightToLeft[3], animatorsRightToLeft[7], animatorsRightToLeft[11]);
        column4AnimationRTL.setStartDelay(getStartDelayForColumn(4, false));
        column4AnimationRTL.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                runAnimation(ANIMATION_RESTART_DELAY / 2);
            }
        });

        column3AnimationRTL = new AnimatorSet();
        column3AnimationRTL.playTogether(animatorsRightToLeft[2], animatorsRightToLeft[6], animatorsRightToLeft[10]);
        column3AnimationRTL.setStartDelay(getStartDelayForColumn(3, false));

        column2AnimationRTL = new AnimatorSet();
        column2AnimationRTL.playTogether(animatorsRightToLeft[1], animatorsRightToLeft[5], animatorsRightToLeft[9]);
        column2AnimationRTL.setStartDelay(getStartDelayForColumn(2, false));

        column1AnimationRTL = new AnimatorSet();
        column1AnimationRTL.playTogether(animatorsRightToLeft[0], animatorsRightToLeft[4], animatorsRightToLeft[8]);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
            scene2.start();
        }
    };

    private Runnable animationRunnableRightToLeft = new Runnable() {
        @Override
        public void run() {
            scene1.start();
        }
    };

    public static void setAnimationTimeBase(int animationTimeBase) {
        ANIMATION_TIME_BASE = animationTimeBase;
    }

    public static void setLagFactor(float lagFactor) {
        LAG_FACTOR = lagFactor;
    }

    public static long getStartDelayForColumn(int columnNumber, boolean isLTR) {
        int ADDITIONAL_LAG_REDUCER = -50;
        if (isLTR) {
            columnNumber = 4 - columnNumber;
            ADDITIONAL_LAG_REDUCER = 0;
        }
        return (long) (ANIMATION_TIME_BASE * 0.3f * columnNumber) + ADDITIONAL_LAG_REDUCER;
    }
}
