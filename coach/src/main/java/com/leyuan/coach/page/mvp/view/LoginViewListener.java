package com.leyuan.coach.page.mvp.view;

/**
 * Created by user on 2016/12/20.
 */

public interface LoginViewListener {


    void loginResult(boolean result);

//    void onGetImageIdetify(String url);

    void onGetIdetify(boolean result,String mobile);
}
