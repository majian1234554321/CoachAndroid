package com.leyuan.commonlibrary.manager;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonCancelListener;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogDoubleButton;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/2/11.
 */

public class PermissionManager {

    private static final int REQUEST_PERMISSION_CODE = 101;
    //    private ArrayList<String> permissions = new ArrayList<>();
//    private ArrayList<String> hints = new ArrayList<>();
    private Activity context;
    private Map<String, String> map;
    private OnCheckPermissionListener listener;

    public PermissionManager(Map<String, String> permissionAndHintMap, Activity context, OnCheckPermissionListener listener) {
        this.context = context;
        this.map = permissionAndHintMap;
        this.listener = listener;
    }

    private void checkPermission(String permission, String hint) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//            Log.i("permission"," checkPermission  PERMISSION_DENIED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                ActivityCompat.requestPermissions(context, new String[]{permission}, REQUEST_PERMISSION_CODE);
            } else {
                showPermissionDailog(permission, hint);
            }
        } else {
//            Log.i("permission"," checkPermission  map.remove(permission);");
            map.remove(permission);
            checkPermissionList();
        }

    }

    private void showPermissionDailog(final String permission, final String hint) {
        new DialogDoubleButton(context)
                .setContentDesc("正常使用该应用需要" + hint + ",请点击确定打开该权限")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        map.remove(permission);
                        checkPermissionList();
                        dialog.dismiss();
                    }
                })
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        ActivityCompat.requestPermissions(context, new String[]{permission}, REQUEST_PERMISSION_CODE);
                        dialog.dismiss();

                    }
                })
                .show();
    }

    public void checkPermissionList() {
//        Log.i("permission","call checkPermissionList");
        if (map == null || map.isEmpty()) {
            listener.checkOver();
            return;
        }
        Iterator<String> iterator = map.keySet().iterator();
        if (iterator.hasNext()) {
            String key = iterator.next();
//            Log.i("permission","key = " +key +"  , hint = " + map.get(key));
            checkPermission(key, map.get(key));
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            String resultPermission = permissions.toString();
//            Log.i("permission"," onRequestPermissionsResult resultPermission = " + resultPermission);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.remove(resultPermission);
            }
            checkPermissionList();
        }
    }

    public interface OnCheckPermissionListener {
        void checkOver();
    }
}
