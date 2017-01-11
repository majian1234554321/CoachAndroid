package com.leyuan.coach.pay;

import android.content.Context;

import com.leyuan.coach.bean.PayOrderBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WeiXinPay implements PayInterface {
	private IWXAPI msgApi;
	public static PayListener payListener;
	public static String appId;
	
	public WeiXinPay(Context context, PayListener payListener) {
		//String appId = context.getString(R.string.weixingAppID);
		msgApi = WXAPIFactory.createWXAPI(context, null);
		//msgApi.registerApp(appId);
		this.payListener = payListener;
	}
	
	@Override
	public void payOrder(PayOrderBean bean) {
		if (bean != null) {
			PayReq payReq = new PayReq();
			appId = bean.getpayOption().getAppid();
			payReq.appId = appId;
			msgApi.registerApp(appId);
			payReq.partnerId = bean.getpayOption().getPartnerid();
			payReq.prepayId = bean.getpayOption().getPrepayid();
			payReq.nonceStr = bean.getpayOption().getNoncestr();
			payReq.timeStamp = bean.getpayOption().getTimestamp();
			payReq.packageValue = bean.getpayOption().get_package();
			payReq.sign = bean.getpayOption().getSign();
			msgApi.sendReq(payReq);
		}
	}
}

