package com.leyuan.commonlibrary.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast toast;
	public static void show(Context context,String txt){
		if(toast == null){
			toast = Toast.makeText(context,"",Toast.LENGTH_LONG);
		}
		toast.setText(txt);
		toast.show();
	}

	public static void showLong(Context context,String txt){
		if(toast == null){
			toast = Toast.makeText(context,"",Toast.LENGTH_LONG);
		}
		toast.setText(txt);
		toast.show();
	}

}
