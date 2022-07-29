package com.chenyacheng.snackbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 动画工具类
 *
 * @author chenyacheng
 * @date 2022/7/29 11:07
 */
public class AnimationUtils {

    public static AnimationUtils getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    public void showAnimation(View view) {
        AnimatorSet drawerSet = new AnimatorSet();
        drawerSet.play(ObjectAnimator.ofFloat(view, "alpha", 0, 1));
        drawerSet.setDuration(500);
        drawerSet.start();
    }

    public void hideAnimation(final ViewGroup viewGroup, final View view) {
        AnimatorSet drawerSet = new AnimatorSet();
        drawerSet.play(ObjectAnimator.ofFloat(view, "alpha", 1, 0));
        drawerSet.setDuration(500);
        drawerSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // No-op
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // No-op
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // No-op
            }
        });
        drawerSet.start();
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final AnimationUtils singleton;

        SingletonEnum() {
            singleton = new AnimationUtils();
        }

        AnimationUtils getInstance() {
            return singleton;
        }
    }
}
