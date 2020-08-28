package com.aqar55.utill;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class Animation {

    public static void LeftToRight_And_Exit(final View child, View Parent, int duration) {

        child.animate()
                .translationXBy(-Parent.getWidth())
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        child.setVisibility(View.GONE);
                    }
                });
    }

    public static void RightToLeft_And_Show(final View child, View Parent, int duration) {
        child.setTranslationX(Parent.getWidth());
        child.animate()
                .translationXBy(-Parent.getWidth())
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        child.setVisibility(View.VISIBLE);
                    }
                });
    }

}
