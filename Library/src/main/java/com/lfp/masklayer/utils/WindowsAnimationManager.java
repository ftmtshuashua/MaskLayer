package com.lfp.masklayer.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.WindowManager;


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
public class WindowsAnimationManager {

    ValueAnimator mBackgrounAnimation = null;

    /**
     * 设置窗口透明度变化到toAlpah
     *
     * @param toAlpah 透明度比例 (0f <= toAlpah <= 1f)
     */
    public void setAnimatinoToAlpah(final Activity activity, float toAlpah) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (lp.alpha == toAlpah) return;
        if (mBackgrounAnimation == null) {
            mBackgrounAnimation = ValueAnimator.ofFloat(lp.alpha, toAlpah);
            mBackgrounAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alp = (float) animation.getAnimatedValue();
                    setWindowsAlpha(activity, alp);
                }
            });
        } else {
            mBackgrounAnimation.cancel();
            mBackgrounAnimation.setFloatValues(lp.alpha, toAlpah);
        }
        mBackgrounAnimation.setDuration(lp.alpha < toAlpah ? 300 : 500);
        mBackgrounAnimation.start();
    }

    //效果设置窗口透明度
    void setWindowsAlpha(Activity activity, float alipha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alipha;
        activity.getWindow().setAttributes(lp);
    }
}
