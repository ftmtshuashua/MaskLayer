package com.lfp.masklayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * <pre>
 * Tip:
 *      蒙层
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 15:25
 * </pre>
 */
public abstract class MaskLayer extends PopupWindow {
    /**
     * 数据处理成功
     */
    public static final int RESULTCODE_OK = 0;
    /**
     * 取消数据处理
     */
    public static final int RESULTCODE_CANCEL = 1;
    /**
     * 数据处理失败
     */
    public static final int RESULTCODE_FAILED = 2;

    /**
     * 非请求模式
     */
    public static final int REQUEST_NO = -1;


    private enum STATE {
        INIT //初始化
        , START //开始
        , PAUSE //暂停
        , DISMISS //关闭
    }

    OnDismissCall mOnDismissCall; //蒙层关闭信号
    int mResultCode = RESULTCODE_OK;
    Intent mResultIntent;
    int mRequestCode = REQUEST_NO;/*来自打启动点的请求码*/
    STATE mState;

    Context mContext;
    MaskLayerManager mMaskLayerManager;
    boolean mIsInit = false;
    Object mTag;


    public MaskLayer(Context context) {
        this.mContext = context;

    }

    public void setMaskLayerManager(MaskLayerManager manager) {
        this.mMaskLayerManager = manager;
    }

    /**
     * 获得蒙层管理器
     *
     * @return
     */
    public MaskLayerManager getMaskLayerManager() {
        if (mMaskLayerManager == null) {
            mMaskLayerManager = generateMaskLayerManager();
        }
        return mMaskLayerManager;

    }

    /**
     * 生成一个默认的蒙层管理器
     *
     * @return
     */
    protected MaskLayerManager generateMaskLayerManager() {
        return new MaskLayerManager(mContext);
    }


    /**
     * 初始化蒙层
     */
    final void init() {
        mState = STATE.INIT;

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //点击空白处时，隐藏掉pop窗口
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        super.setOnDismissListener(mOnDisLsr);

        onInit();

        View view = onCreateView(LayoutInflater.from(mContext));
        setContentView(view);
        onViewCreate(view);

        mIsInit = true;
    }

    /**
     * 判断是否已经初始化
     *
     * @return
     */
    public boolean isInit() {
        return mIsInit;
    }

    /**
     * 当初始化蒙层的时候
     */
    protected void onInit() {

    }

    /**
     * 返回蒙层的View
     */
    protected abstract View onCreateView(LayoutInflater inflater);

    protected void onViewCreate(View view) {
    }

    protected Context getContext() {
        return mContext;
    }


    /**
     * 当蒙层准备显示的时候
     */
    final void callStart() {
        mState = STATE.START;
        onStart();
    }

    final void callResume() {
        mState = STATE.START;
        onResume();
    }

    final void callPause() {
        mState = STATE.PAUSE;
        onPause();
    }

    /**
     * 当蒙层关闭的时候
     */
    final void callDismiss() {
        mState = STATE.DISMISS;
        onDismiss();
    }

    protected void onStart() {
    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onDismiss() {

    }

    /* PopupWindow关闭监听 */
    PopupWindow.OnDismissListener mOnDisLsr = new OnDismissListener() {
        @Override
        public void onDismiss() {
            if (mOnDismissCall != null) mOnDismissCall.onDismiss(MaskLayer.this);
            if (mOnDismissListener != null) mOnDismissListener.onDismiss();
        }
    };

    private OnDismissListener mOnDismissListener;

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Object getTag() {
        return this.mTag;
    }

    /**
     * 回调
     *
     * @param requestcode
     * @param resultcode
     * @param intent
     */
    protected void onRequestResult(int requestcode, int resultcode, Intent intent) {
    }

    /**
     * 显示当前图层
     */
    public void show() {
        show(this);
    }

    public void show(MaskLayer layer) {
        show(layer, REQUEST_NO);
    }

    public void show(MaskLayer layer, int requestcode) {
        layer.setMaskLayerManager(getMaskLayerManager());
        layer.getMaskLayerManager().show(layer, requestcode);
    }

    public void setResult(int resultcode) {
        setResult(resultcode, null);
    }

    public void setResult(int resultcode, Intent intent) {
        this.mResultCode = resultcode;
        this.mResultIntent = intent;
    }


}
