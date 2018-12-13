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
public class Layer3 extends BaseLayer {

    public Layer3(Context context) {
        super(context);
    }

    @Override
    protected void onViewCreate(View view) {
        super.onViewCreate(view);
        setTitle("第三层");
        setButton("关闭所有层");
    }

    @Override
    protected int getRootHeight() {
        return 220;
    }

    @Override
    protected int getBacnkgrounRes() {
        return R.color.color_60b11c;
    }

    @Override
    public void onClick(View v) {
        getMaskLayerManager().dismissAllLayer();
    }
}
