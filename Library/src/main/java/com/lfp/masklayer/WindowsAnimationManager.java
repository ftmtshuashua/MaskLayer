package com.lfp.masklayer;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.WindowManager;

import java.lang.ref.WeakReference;


/**
 * <pre>
 * Tip:
 *      窗口渐变动画工具
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 16:02
 * </pre>
 */
class WindowsAnimationManager implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    /**
     * 动画持续时间
     */
    public static final long DURATION_TIME = 300;
    ValueAnimator mBackgrounAnimation = null;
    WeakReference<Activity> activity;

    public WindowsAnimationManager(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }


    /**
     * 设置窗口透明度变化到toAlpah
     *
     * @param toAlpah 透明度比例 (0f <= toAlpah <= 1f)
     */
    public void setAlpah(float toAlpah) {
        float alpha = getWindowsAlpha();
        if (mBackgrounAnimation != null) {
            mBackgrounAnimation.removeUpdateListener(this);
            mBackgrounAnimation.removeListener(this);
            mBackgrounAnimation.cancel();
            mBackgrounAnimation = null;
        }

        mBackgrounAnimation = ValueAnimator.ofFloat(alpha, toAlpah);
        mBackgrounAnimation.addUpdateListener(this);
        mBackgrounAnimation.addListener(this);
        mBackgrounAnimation.setDuration(DURATION_TIME);
        mBackgrounAnimation.start();
    }


    float getWindowsAlpha() {
        Activity activity = this.activity.get();
        if (activity != null) {
            return activity.getWindow().getAttributes().alpha;
        }
        return 0;
    }


    //效果设置窗口透明度
    void setWindowsAlpha(Activity activity, float alipha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alipha;
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float alp = (float) animation.getAnimatedValue();

        Activity activity = this.activity.get();
        if (activity != null) {
            setWindowsAlpha(activity, alp);
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mBackgrounAnimation = null;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        mBackgrounAnimation = null;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
