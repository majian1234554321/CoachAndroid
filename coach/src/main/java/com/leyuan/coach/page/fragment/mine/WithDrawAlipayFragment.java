package com.leyuan.coach.page.fragment.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.leyuan.coach.R;
import com.leyuan.coach.page.mvp.presenter.WithDrawPresenter;
import com.leyuan.coach.page.mvp.view.WithDrawViewListener;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogSingleButton;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.StringUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

/**
 * Created by user on 2017/1/16.
 */
public class WithDrawAlipayFragment extends Fragment implements View.OnClickListener, WithDrawViewListener {

    private static final java.lang.String TAG = "WithDrawAlipayFragment";
    private String accout;
    private String name;
    private String number;
    private WithDrawPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_alipay, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bt_withdraw).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new WithDrawPresenter(getActivity(), this);
    }

    private EditText getEditAccount() {
        return (EditText) getView().findViewById(R.id.edit_account);
    }

    private EditText getEditName() {
        return (EditText) getView().findViewById(R.id.edit_name);
    }

    private EditText getEditNumber() {
        return (EditText) getView().findViewById(R.id.edit_number);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_withdraw:

                if (verify()) {
                    DialogUtils.showDialog(getActivity(), "", false);
                    presenter.withdraw(accout, name, number);
                }
                break;
        }
    }

    private boolean verify() {
        accout = getEditAccount().getText().toString().trim();
        if (!StringUtils.isAlipayAccout(accout)) {
            ToastUtil.showLong(getActivity(), getResources().getString(R.string.alipay_accout_errror));
            return false;
        }
        name = getEditName().getText().toString().trim();

        LogUtil.i(TAG,"name.length = " + name.length() +" name.getbytes length = " +name.getBytes().length);
        if (name.getBytes().length < 4) {
            ToastUtil.showLong(getActivity(), getResources().getString(R.string.alipay_name_errror));
            return false;
        }

        number = getEditNumber().getText().toString().trim();
        try {
            int num = Integer.parseInt(number);
            if (num < 200) {
                ToastUtil.showLong(getActivity(), getResources().getString(R.string.less_than_two_hundred_erros));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.showLong(getActivity(), getResources().getString(R.string.please_input_integer));
            return false;
        }
        return true;
    }

    @Override
    public void onWithDrawResult(boolean success) {
        DialogUtils.dismissDialog();
        if (success) {
            new DialogSingleButton(getActivity())
                    .setContentDesc(getResources().getString(R.string.withdraw_success))
                    .setBtnOkListener(new ButtonOkListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            getActivity().finish();
                        }
                    }).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}
