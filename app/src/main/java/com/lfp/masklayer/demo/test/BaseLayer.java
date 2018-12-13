package com.lfp.masklayer.demo.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.masklayer.MaskLayer;
import com.lfp.masklayer.demo.R;
import com.lfp.masklayer.demo.ScreenUtils;

import java.text.MessageFormat;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 17:22
 * </pre>
 */
public abstract class BaseLayer extends MaskLayer implements View.OnClickListener {
    public BaseLayer(Context context) {
        super(context);
    }

    TextView mTV_Title, mTV_Msg, mBT_Button;


    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.layer_test, parent , true);
    }


    @Override
    protected void onViewCreate(View view) {
        super.onViewCreate(view);

        mTV_Title = view.findViewById(R.id.view_Title);
        mTV_Msg = view.findViewById(R.id.view_Msg);
        mBT_Button = view.findViewById(R.id.view_Button);
        mBT_Button.setOnClickListener(this);
        View root = view.findViewById(R.id.view_Root);
        root.setBackgroundResource(getBacnkgrounRes());
        root.getLayoutParams().height = ScreenUtils.dip2px(getContext(), getRootHeight());
    }

    protected abstract int getRootHeight();

    protected abstract int getBacnkgrounRes();

    public void setTitle(CharSequence sequence) {
        mTV_Title.setText(sequence);
    }

    public void setButton(CharSequence sequence) {
        mBT_Button.setText(sequence);
    }

    @Override
    protected void onInit() {
        super.onInit();
        Log.e("Log", "OnInit()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Log", "onStart()");
        log("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Log", "onResume()");
        log("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Log", "onPause()");
        log("onPause()");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("Log", "onDismiss()");
        log("onDismiss()");
    }

    @Override
    protected void onRequestResult(int requestcode, int resultcode, Intent intent) {
        super.onRequestResult(requestcode, resultcode, intent);
        Log.e("Log", MessageFormat.format("onRequestResult({0},{1},{2})", requestcode, resultcode, intent));
        log(MessageFormat.format("onRequestResult({0},{1},{2})", requestcode, resultcode, intent));
    }

    void log(CharSequence sequence) {
        if (mTV_Msg != null) {
            mTV_Msg.append(sequence);
            mTV_Msg.append("\n");
        }
    }
}
