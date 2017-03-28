package com.landicorp.nuoxin.net;

import org.json.JSONObject;

import okhttp3.Response;

public interface ReqCallBack<T> {
	/**
	 * 响应成功
	 */
	void onReqSuccess(NetWorkAction action, T result);

	/**
	 * 响应失败
	 */
	void onReqFailed(String errorMsg);
}
