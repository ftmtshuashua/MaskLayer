package com.lfp.masklayer.demo.test;

import android.content.Context;
import android.view.View;

import com.lfp.masklayer.demo.R;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 17:08
 * </pre>
 */
public class Layer2 extends BaseLayer {

    public Layer2(Context context) {
        super(context);
    }

    @Override
    protected void onViewCreate(View view) {
        super.onViewCreate(view);
        setTitle("第二层");
    }

    @Override
    protected int getRootHeight() {
        return 260;
    }

    @Override
    protected int getBacnkgrounRes() {
        return R.color.color_0076ff;
    }

    @Override
    public void onClick(View v) {
        startLayer(new Layer3(getContext()));
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        setResult(8888);
    }
}
