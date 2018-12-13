package com.lfp.masklayer;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.lfp.masklayer.utils.WindowsAnimationManager;

import java.util.Stack;

/**
 * <pre>
 * Tip:
 *      蒙层管理器
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 15:26
 * </pre>
 */
public class MaskLayerManager {

    /**
     * 关闭窗口动画
     */
    public static final int FLAG_WINDOWS_ANIMATION_DISABLED = 0x00000001;

    WindowsAnimationManager mWindowsAnimationManager;
    Context mContext;
    Stack<MaskLayer> mOpenLayer = new Stack<>();//蒙层栈管理
    int mFlag;

    public MaskLayerManager(Context context) {
        this.mContext = context;
        disabledWindowsAnimation();
    }

    /**
     * 获得上下文
     *
     * @return The context
     */

    public Context getContext() {
        return mContext;
    }

    /**
     * 显示一个蒙层
     *
     * @param layer The layer
     */
    public final void show(MaskLayer layer) {
        show(layer, MaskLayer.REQUEST_NO);
    }

    /**
     * 显示一个蒙层
     *
     * @param layer       The layer
     * @param requestcode 请求码
     */
    public final void show(MaskLayer layer, int requestcode) {
        layer.mRequestCode = requestcode;

        final int layercount = getLayerCount();
        if (layercount == 0 && isEneblaeWindowsAnimation()) {
            if (mContext instanceof Activity)
                getWindowsAnimationManager().setAnimatinoToAlpah((Activity) mContext, 0.3f);
        }

        if (layercount > 0) {
            peek().callPause();
        }
        push(layer);
        if (!layer.isInit()) {
            layer.init();
        }
        layer.callStart();
        onShow(layer);
        layer.callResume();
    }

    /**
     * 关闭蒙层
     *
     * @param layer The layer
     */
    private final void close(MaskLayer layer) {
        layer.mOnDismissCall = null;
        layer.callPause();
        layer.callDismiss();
        layer.dismiss();

        boolean isTopLayer = layer == peek();//是否为顶层View
        mOpenLayer.remove(layer);
        if (isTopLayer) {
            MaskLayer top = peek();
            if (top != null) {
                top.callResume();
                if (layer.mRequestCode != MaskLayer.REQUEST_NO) {
                    top.onRequestResult(layer.mRequestCode, layer.mResultCode, layer.mResultIntent);
                }
            }
        }


        if (getLayerCount() == 0 && isEneblaeWindowsAnimation()) {
            if (mContext instanceof Activity)
                getWindowsAnimationManager().setAnimatinoToAlpah((Activity) mContext, 1f);
        }
    }

    /**
     * 获得Windows动画管理器
     *
     * @return Windows动画管理器
     */
    public WindowsAnimationManager getWindowsAnimationManager() {
        if (mWindowsAnimationManager == null)
            mWindowsAnimationManager = new WindowsAnimationManager();
        return mWindowsAnimationManager;
    }

    /**
     * 当需要显示蒙层的时候，制定蒙层显示的规则
     *
     * @param layer 被显示的layer
     */
    protected void onShow(MaskLayer layer) {
        if (mContext instanceof Activity) {
            //底部弹出
            layer.setAnimationStyle(R.style.lib_PopupAnimation_Translation_UpDown);
            layer.showAtLocation(((Activity) mContext).findViewById(android.R.id.content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    /**
     * 获得栈中蒙层数量
     *
     * @return The count
     */
    private int getLayerCount() {
        return mOpenLayer.size();
    }

    /**
     * 查看顶部蒙层
     *
     * @return The top layer
     */
    private MaskLayer peek() {
        if (mOpenLayer.isEmpty()) return null;
        return mOpenLayer.peek();
    }

    /**
     * 提交一个蒙层给管理器
     *
     * @param layer The layer
     * @return The layer
     */
    private MaskLayer push(MaskLayer layer) {
        layer.mOnDismissCall = mOnDismissCall;
        return mOpenLayer.push(layer);
    }

    /**
     * 禁用窗口动画
     */
    public void disabledWindowsAnimation() {
        mFlag |= FLAG_WINDOWS_ANIMATION_DISABLED;
    }

    /**
     * 启用窗口动画
     */
    public void enabileWindowsAnimation() {
        mFlag &= ~FLAG_WINDOWS_ANIMATION_DISABLED;
    }

    /**
     * 判断是否需要显示窗口动画
     *
     * @return 是否显示窗口动画
     */
    public boolean isEneblaeWindowsAnimation() {
        return (mFlag & FLAG_WINDOWS_ANIMATION_DISABLED) != 0;
    }

    /**
     * 关闭所有蒙层
     */
    public void dismissAllLayer() {
        while (!mOpenLayer.isEmpty()) {
            close(mOpenLayer.pop());
        }
    }


    private OnDismissCall mOnDismissCall = new OnDismissCall() {
        @Override
        public void onDismiss(MaskLayer layer) {
            close(layer);
        }
    };

}
