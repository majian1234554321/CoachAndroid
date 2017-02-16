package com.leyuan.coach.page.mvp.view;

import com.leyuan.commonlibrary.manager.PermissionManager;

/**
 * Created by user on 2017/1/4.
 */
public interface AutoLoginViewListener extends PermissionManager.OnCheckPermissionListener {
    void onAutoLoginResult(boolean success);

    @Override
    void checkOver();
}
