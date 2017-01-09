package com.leyuan.coach.pay;


import com.leyuan.coach.bean.PayOrderBean;

public interface PayInterface {
     void payOrder(PayOrderBean payOrderBean);

     interface PayListener {
        void fail(String code, Object object);
        void success(String code, Object object);
    }
}

