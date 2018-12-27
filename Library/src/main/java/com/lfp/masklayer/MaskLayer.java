package com.lfp.masklayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * <pre>
 * Tip:
 *      创建一个基于PopupWindow的蒙层
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

    /**
     * 设置蒙层管理器
     *
     * @param manager 蒙层管理器
     */
    public void setMaskLayerManager(MaskLayerManager manager) {
        this.mMaskLayerManager = manager;
    }

    /**
     * 获得蒙层管理器
     *
     * @return 蒙层管理器
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
     * @return 蒙层管理器
     */
    protected MaskLayerManager generateMaskLayerManager() {
        return new MaskLayerManager(mContext);
    }


    /**
     * 初始化蒙层 ，这里配置了PopupWindow的一些默认行为，这些行为可以在OnInit()中被改变
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

        ViewGroup rootView = getRootView(getContext());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(getWidth(), getHeight()));

        View view = onCreateView(LayoutInflater.from(mContext), rootView);
        rootView.addView(view);

        setContentView(rootView);
        onViewCreate(view);

        mIsInit = true;
    }

    FrameLayout mPrentView; //跟布局

    /*获得一个跟布局，为了正确的显示ContentView*/
    protected ViewGroup getRootView(Context context) {
        if (mPrentView == null) {
            mPrentView = new FrameLayout(context);
        }
        return mPrentView;
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
     * 当初始化蒙层的时候被调用，在这里做一些PopupWindow行为的配置
     */
    protected void onInit() {

    }

    /**
     * 或蒙层的视图，这个视图将被显示在PopupWindow上
     *
     * @param inflater 布局加载器
     * @param parent   蒙层的跟布局
     * @return 蒙层的视图
     */
    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    /**
     * 当视图被创建，并且已绑定到PopupWindow上的时候被调用
     *
     * @param view 通过onCreateView()获得的视图
     */
    protected void onViewCreate(View view) {
    }

    /**
     * 获得上下文
     *
     * @return 上下文
     */
    protected Context getContext() {
        return mContext;
    }


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

    final void callDismiss() {
        mState = STATE.DISMISS;
        onDismiss();
    }

    /**
     * 当Show()被调用的时候
     */
    protected void onStart() {
    }

    /**
     * 当蒙层现在在前台的时候
     */
    protected void onResume() {

    }

    /**
     * 当另外一个蒙层被打开，当前蒙层被遮挡的时候
     */
    protected void onPause() {

    }

    /**
     * 当蒙层被关闭的时候
     */
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

    /**
     * 设置tag
     *
     * @param tag The tag
     */
    public void setTag(Object tag) {
        this.mTag = tag;
    }

    /**
     * 获得tag
     *
     * @return The tag
     */
    public Object getTag() {
        return this.mTag;
    }

    /**
     * 通过show(layer,requestcode)方法打开了一个蒙层，并且requestcode != -1。
     * 在打开蒙层关闭，当前蒙层显示的时候回调该方法。
     * 打开的蒙层通过setResult方法设置回复的信息
     *
     * @param requestcode 请求码
     * @param resultcode  回复码
     * @param intent      回复数据
     */
    protected void onRequestResult(int requestcode, int resultcode, Intent intent) {
    }

    /**
     * 显示当前图层
     */
    public void show() {
        getMaskLayerManager().show(this);
    }

    /**
     * 显示一个蒙层
     *
     * @param layer The layer
     */
    public void startLayer(MaskLayer layer) {
        startLayer(layer, REQUEST_NO);
    }

    /**
     * 通过请求码显示一个蒙层
     *
     * @param layer       The layer
     * @param requestcode 请求码
     */
    public void startLayer(MaskLayer layer, int requestcode) {
        final MaskLayerManager maskLayerManager = getMaskLayerManager();
        layer.setMaskLayerManager(maskLayerManager);
        maskLayerManager.show(layer, requestcode);
    }

    /**
     * 设置回复数据
     *
     * @param resultcode 回复码
     */
    public void setResult(int resultcode) {
        setResult(resultcode, null);
    }

    /**
     * 设置回复数据
     *
     * @param resultcode 回复码
     * @param intent     回复数据
     */
    public void setResult(int resultcode, Intent intent) {
        this.mResultCode = resultcode;
        this.mResultIntent = intent;
    }


}
