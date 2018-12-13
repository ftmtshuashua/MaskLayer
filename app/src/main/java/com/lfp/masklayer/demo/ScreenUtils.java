package com.lfp.masklayer.demo;

import android.content.Context;
import android.util.TypedValue;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/13 09:34
 * </pre>
 */
public class ScreenUtils {

    /**
     * DP 转 PX
     *
     * @param dp dp
     * @return int
     */
    public static int dip2px(Context context, float dp) {
        return (int) applyDimension(context, TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    public static float applyDimension(Context context, int unit, float value) {
        return TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }
}
