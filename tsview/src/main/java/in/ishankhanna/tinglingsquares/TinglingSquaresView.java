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

    private void init() {
        for (int m=0;m<3;m++) {
            for (int n=0;n<4;n++) {
                squareViews[m][n] = new SquareView(ctx, m, n);
            }
        }

        pvhLeftToRightAnimation = PropertyValuesHolder.ofFloat(View.ROTATION, 0, 90);
        pvhRightToLeftAnimation = PropertyValuesHolder.ofFloat(View.ROTATION, 90, 0);

        int m=0,n=0;
        for (int i=0;i<12; i++) {
            animatorsLeftToRight[i] = ObjectAnimator.ofPropertyValuesHolder(squareViews[m][n], pvhLeftToRightAnimation);
            switch (m) {
                case 0:
                    animatorsLeftToRight[i].setDuration((int)(LEFT_TO_RIGHT_ANIM_TIME * 0.75f));
                    break;
                case 1:
                    animatorsLeftToRight[i].setDuration((int)(LEFT_TO_RIGHT_ANIM_TIME * 0.40f));
                    break;
                case 2:
                    animatorsLeftToRight[i].setDuration((int)(LEFT_TO_RIGHT_ANIM_TIME * 0.60f));
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
            n++;
            if (n > 3) {
                m++;
                n = 0;
            }
        }

        column4AnimationLTR = new AnimatorSet();
        column4AnimationLTR.play(animatorsLeftToRight[3]).with(animatorsLeftToRight[11]);

        column3AnimationLTR = new AnimatorSet();
        column3AnimationLTR.play(animatorsLeftToRight[2]).with(animatorsLeftToRight[10]);

        column2AnimationLTR = new AnimatorSet();
        column2AnimationLTR.play(animatorsLeftToRight[1]).with(animatorsLeftToRight[9]);

        column1AnimationLTR = new AnimatorSet();
        column1AnimationLTR.play(animatorsLeftToRight[0]).with(animatorsLeftToRight[8]);

        column1AnimationLTR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setupLeftToRightAnimationTriggers();
                runAnimation(500);
                System.out.println("Animation finished");
            }
        });

        setupLeftToRightAnimationTriggers();

        column1AnimationRTL = new AnimatorSet();
        column1AnimationRTL.play(animatorsRightToLeft[4]).with(animatorsRightToLeft[0]);

        column2AnimationRTL = new AnimatorSet();
        column2AnimationRTL.play(animatorsRightToLeft[5]).with(animatorsRightToLeft[1]);

        column3AnimationRTL = new AnimatorSet();
        column3AnimationRTL.play(animatorsRightToLeft[6]).with(animatorsRightToLeft[2]);

        column4AnimationRTL = new AnimatorSet();
        column4AnimationRTL.play(animatorsRightToLeft[7]).with(animatorsRightToLeft[3]);

        column4AnimationRTL.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setupRightToLeftAnimationTriggers();
                runAnimation(500);
                System.out.println("Animation RTL Finished");
            }
        });

        setupRightToLeftAnimationTriggers();
    }

    private void setupLeftToRightAnimationTriggers() {
        animatorsLeftToRight[7].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column4AnimationLTR.start();
                    animatorsLeftToRight[7].removeAllUpdateListeners();
                }

            }
        });

        animatorsLeftToRight[6].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column3AnimationLTR.start();
                    animatorsLeftToRight[6].removeAllUpdateListeners();
                }

            }
        });

        animatorsLeftToRight[5].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column2AnimationLTR.start();
                    animatorsLeftToRight[5].removeAllUpdateListeners();
                }

            }
        });

        animatorsLeftToRight[4].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column1AnimationLTR.start();
                    animatorsLeftToRight[4].removeAllUpdateListeners();
                }

            }
        });

        animatorsLeftToRight[3].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsLeftToRight[6].start();
                    animatorsLeftToRight[3].removeAllUpdateListeners();
                }
            }
        });

        animatorsLeftToRight[2].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsLeftToRight[5].start();
                    animatorsLeftToRight[2].removeAllUpdateListeners();
                }
            }
        });

        animatorsLeftToRight[1].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsLeftToRight[4].start();
                    animatorsLeftToRight[1].removeAllUpdateListeners();
                }
            }
        });

    }

    private void setupRightToLeftAnimationTriggers() {

        animatorsRightToLeft[8].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column1AnimationRTL.start();
                    animatorsRightToLeft[8].removeAllUpdateListeners();
                }

            }
        });

        animatorsRightToLeft[4].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsRightToLeft[9].start();
                    animatorsRightToLeft[4].removeAllUpdateListeners();
                }

            }
        });

        animatorsRightToLeft[9].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column2AnimationRTL.start();
                    animatorsRightToLeft[9].removeAllUpdateListeners();
                }
            }
        });

        animatorsRightToLeft[5].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsRightToLeft[10].start();
                    animatorsRightToLeft[5].removeAllUpdateListeners();
                }
            }
        });

        animatorsRightToLeft[10].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column3AnimationRTL.start();
                    animatorsRightToLeft[10].removeAllUpdateListeners();
                }
            }
        });

        animatorsRightToLeft[6].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.75f) {
                    animatorsRightToLeft[11].start();
                    animatorsRightToLeft[6].removeAllUpdateListeners();
                }
            }
        });

        animatorsRightToLeft[11].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (animation.getAnimatedFraction() > 0.25f) {
                    column4AnimationRTL.start();
                    animatorsRightToLeft[11].removeAllUpdateListeners();
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
            animatorsLeftToRight[7].start();
        }
    };

    private Runnable animationRunnableRightToLeft = new Runnable() {
        @Override
        public void run() {
            animatorsRightToLeft[8].start();
        }
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
