package com.leyuan.coach.pay;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.leyuan.coach.bean.PayOrderBean;
import com.leyuan.coach.bean.PayResult;


public class AliPay implements PayInterface {
    private Context context;
    private PayListener payListener;

    public AliPay(Context context, PayListener payListener) {
        this.context = context;
        this.payListener = payListener;
    }

    @Override
    public void payOrder(PayOrderBean bean) {
        if (null != bean) {
            final String orderInfo = bean.getpayOption().getPayString();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        PayTask aliPay = new PayTask((Activity)context);
                        final String result = aliPay.pay(orderInfo);
                        if (null != payListener) {
                            if (!TextUtils.isEmpty(result)) {
                                final PayResult payResult = new PayResult(result);
                                final String code = payResult.getResultStatus();
                                switch (code) {
                                    case "9000":
                                        ((Activity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.success("9000", payResult);
                                            }
                                        });
                                        break;

                                    default:
                                        ((Activity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.fail(code, result);
                                            }
                                        });
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }
}
