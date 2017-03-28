package com.landicorp.yinshang.data;

import com.landicorp.yinshang.data.response.BaseResponse;

/**
 * Created by u on 2017/1/9.
 */

public interface ReqCallBack<T> {
	/**
	 * 响应成功
	 */
	void onReqSuccess(T result);

	/**
	 * 响应失败
	 */
	void onReqFailed(String errorMsg);
}
