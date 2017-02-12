package com.leyuan.coach.page.fragment.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.presenter.WithDrawPresenter;
import com.leyuan.coach.page.mvp.view.WithDrawViewListener;
import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogSingleButton;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

/**
 * Created by user on 2017/1/16.
 */
public class WithDrawBankFragment extends Fragment implements View.OnClickListener, WithDrawViewListener {

    private String number;
    private WithDrawPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_bank, null);
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
        UserCoach userCoach = App.getInstance().getUser();
        if (userCoach != null) {
            getTxtName().setText(userCoach.getName());
            getTxtBank().setText(userCoach.getBankName());
            getTxtBankCard().setText(userCoach.getBankNo());
        }

    }

    private EditText getTxtName() {
        return (EditText) getView().findViewById(R.id.txt_name);
    }

    private EditText getTxtBank() {
        return (EditText) getView().findViewById(R.id.txt_bank);
    }

    private EditText getTxtBankCard() {
        return (EditText) getView().findViewById(R.id.txt_bank_card);
    }

    private EditText getEditMoneyNumber() {
        return (EditText) getView().findViewById(R.id.edit_money_number);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_withdraw:

                number = getEditMoneyNumber().getText().toString().trim();
                try {
                    int num = Integer.parseInt(number);
                    if (num < 200) {
                        ToastUtil.showLong(getActivity(), getResources().getString(R.string.less_than_two_hundred_erros));
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ToastUtil.showLong(getActivity(), getResources().getString(R.string.please_input_integer));
                    return;
                }
                DialogUtils.showDialog(getActivity(), "", false);
                presenter.withdrawBank(number);
                break;
        }
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
