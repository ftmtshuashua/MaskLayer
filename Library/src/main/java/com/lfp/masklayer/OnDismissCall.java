package com.lfp.masklayer;

/**
 * <pre>
 * Tip:
 *      蒙城关闭的时候给管理器发送一个信号
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/12 17:02
 * </pre>
 */
interface OnDismissCall {
    void onDismiss(MaskLayer layer);
}
