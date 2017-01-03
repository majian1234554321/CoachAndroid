package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.mvp.presenter.WithDrawPresenter;
import com.leyuan.coach.page.mvp.view.WithDrawViewListener;
import com.leyuan.commonlibrary.util.StringUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

/**
 * Created by user on 2016/12/28.
 */
public class WithDrawActivity extends BaseActivity implements View.OnClickListener, WithDrawViewListener {

    private String accout;
    private String name;
    private String number;
    private WithDrawPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_withdraw);
        presenter = new WithDrawPresenter(this, this);

        findViewById(R.id.bt_withdraw).setOnClickListener(this);
    }

    private EditText getEditAccount() {
        return (EditText) findViewById(R.id.edit_account);
    }

    private EditText getEditName() {
        return (EditText) findViewById(R.id.edit_name);
    }

    private EditText getEditNumber() {
        return (EditText) findViewById(R.id.edit_number);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_withdraw:

                if (verify()) {
                    presenter.withdraw(accout, name, number);
                }
                break;
        }
    }

    private boolean verify() {
        accout = getEditAccount().getText().toString().trim();
        if (!StringUtils.isAlipayAccout(accout)) {
            ToastUtil.showLong(this, getResources().getString(R.string.alipay_accout_errror));
            return false;
        }
        name = getEditName().getText().toString().trim();
        if (name.length() < 4) {
            ToastUtil.showLong(this, getResources().getString(R.string.alipay_name_errror));
            return false;
        }

        number = getEditNumber().getText().toString().trim();
        try {
            int num = Integer.parseInt(number);
            if (num < 200) {
                ToastUtil.showLong(this, getResources().getString(R.string.less_than_two_hundred_erros));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.showLong(this, getResources().getString(R.string.please_input_integer));
            return false;
        }
        return true;
    }

    @Override
    public void onWithDrawResult(boolean success) {
        ToastUtil.showLong(this, getResources().getString(R.string.withdraw_success));

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
