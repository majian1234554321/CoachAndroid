package com.leyuan.commonlibrary.util;

import android.content.Context;
import android.widget.Toast;

@Deprecated
public class ToastUtil {

    private static Toast toast;

    @Deprecated
    public static void show(Context context, String txt) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(txt);
        toast.show();
    }

    @Deprecated
    public static void showLong(Context context, String txt) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        toast.setText(txt);
        toast.show();
    }

}
