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
public class Layer1 extends BaseLayer {

    public Layer1(Context context) {
        super(context);
    }

    @Override
    protected void onViewCreate(View view) {
        super.onViewCreate(view);
        setTitle("第一层");
    }

    @Override
    protected int getRootHeight() {
        return 300;
    }

    @Override
    protected int getBacnkgrounRes() {
        return R.color.color_f79b01;
    }

    @Override
    public void onClick(View v) {
        show(new Layer2(getContext()), 666);
    }
}
