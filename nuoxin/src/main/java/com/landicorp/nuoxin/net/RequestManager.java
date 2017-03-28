package com.landicorp.nuoxin.net;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager {

	private static final String TAG = RequestManager.class.getSimpleName();
	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
	private static final String BASE_URL = "http://xxx.com/openapi";//请求接口根地址
	public static final int TYPE_GET = 0;//get请求
	public static final int TYPE_POST_JSON = 1;//post请求参数为json
	public static final int TYPE_POST_FORM = 2;//post请求参数为表单
	private static volatile RequestManager mInstance;//单利引用
	private OkHttpClient okHttpClient;
	private Handler okHttpHandler;//全局处理子线程和M主线程通信

	/**
	 * 初始化RequestManager
	 */
	public RequestManager(Context context) {
		//初始化OkHttpClient
		okHttpClient = new OkHttpClient().newBuilder()
				.connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
				.readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
				.writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
				.build();
//		//初始化Handler
		okHttpHandler = new Handler(context.getMainLooper());
	}

	/**
	 * 获取单例引用
	 *
	 * @return
	 */
	public static RequestManager getInstance(Context context) {
		RequestManager inst = mInstance;
		if (inst == null) {
			synchronized (RequestManager.class) {
				inst = mInstance;
				if (inst == null) {
					inst = new RequestManager(context.getApplicationContext());
					mInstance = inst;
				}
			}
		}
		return inst;
	}

	/**
	 * okHttp异步请求统一入口
	 * @param actionUrl   接口地址
	 * @param requestType 请求类型
	 * @param paramsMap   请求参数
	 * @param callBack 请求返回数据回调
	 * @param <T> 数据泛型
	 **/
	public <T> Call requestAsyn(String actionUrl, int requestType, HashMap<String, String> paramsMap, NetWorkAction action, ReqCallBack<T> callBack) {
		Call call = null;
		switch (requestType) {
			case TYPE_GET:
				call = requestGetByAsyn(actionUrl, paramsMap, action, callBack);
				break;
			case TYPE_POST_JSON:
				call = requestPostByAsyn(actionUrl, paramsMap, action, callBack);
				break;
			case TYPE_POST_FORM:
				call = requestPostByAsynWithForm(actionUrl, paramsMap, action, callBack);
				break;
		}
		return call;
	}

	/**
	 * okHttp get异步请求
	 * @param actionUrl 接口地址
	 * @param paramsMap 请求参数
	 * @param callBack 请求返回数据回调
	 * @param <T> 数据泛型
	 * @return
	 */
	private <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap,final NetWorkAction action, final ReqCallBack<T> callBack) {
		StringBuilder tempParams = new StringBuilder();
		try {
			int pos = 0;
			for (String key : paramsMap.keySet()) {
				if (pos > 0) {
					tempParams.append("&");
				}
				tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
				pos++;
			}
			String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
			final Request request = addHeaders().url(requestUrl).build();
			final Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					failedCallBack("访问失败", callBack);
					Log.e(TAG, e.toString());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (response.isSuccessful()) {
						String string = response.body().string();
						Log.e(TAG, "response ----->" + string);
						successCallBack(action, (T) string, callBack);
					} else {
						failedCallBack("服务器错误", callBack);
					}
				}
			});
			return call;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return null;
	}

	/**
	 * okHttp post异步请求
	 * @param actionUrl 接口地址
	 * @param paramsMap 请求参数
	 * @param callBack 请求返回数据回调
	 * @param <T> 数据泛型
	 * @return
	 */
	private <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final NetWorkAction action, final ReqCallBack<T> callBack) {
		try {
			StringBuilder tempParams = new StringBuilder();
			int pos = 0;
			for (String key : paramsMap.keySet()) {
				if (pos > 0) {
					tempParams.append("&");
				}
				tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
				pos++;
			}
			String params = tempParams.toString();
			RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
			String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
			final Request request = addHeaders().url(requestUrl).post(body).build();
			final Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					failedCallBack("访问失败", callBack);
					Log.e(TAG, e.toString());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (response.isSuccessful()) {
						String string = response.body().string();
						Log.e(TAG, "response ----->" + string);
						successCallBack(action, (T) string, callBack);
					} else {
						failedCallBack("服务器错误", callBack);
					}
				}
			});
			return call;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return null;
	}

	/**
	 * okHttp post异步请求表单提交
	 * @param actionUrl 接口地址
	 * @param paramsMap 请求参数
	 * @param callBack 请求返回数据回调
	 * @param <T> 数据泛型
	 * @return
	 */
	private <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final NetWorkAction action, final ReqCallBack<T> callBack) {
		try {
			FormBody.Builder builder = new FormBody.Builder();
			for (String key : paramsMap.keySet()) {
				builder.add(key, paramsMap.get(key));
			}
			RequestBody formBody = builder.build();
			String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
			final Request request = addHeaders().url(requestUrl).post(formBody).build();
			final Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					failedCallBack("访问失败", callBack);
					Log.e(TAG, e.toString());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (response.isSuccessful()) {
						String string = response.body().string();
						Log.e(TAG, "response ----->" + string);
						successCallBack(action, (T) string, callBack);
					} else {
						failedCallBack("服务器错误", callBack);
					}
				}
			});
			return call;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return null;
	}

	/**
	 * 统一为请求添加头信息
	 * @return
	 */
	private Request.Builder addHeaders() {
		Request.Builder builder = new Request.Builder()
				.addHeader("Connection", "keep-alive")
				.addHeader("platform", "2")
				.addHeader("phoneModel", Build.MODEL)
				.addHeader("systemVersion", Build.VERSION.RELEASE)
				.addHeader("appVersion", "3.2.0");
		return builder;
	}

	/**
	 * 统一同意处理成功信息
	 * @param result
	 * @param callBack
	 * @param <T>
	 */
	private <T> void successCallBack(final NetWorkAction action, final  T result, final ReqCallBack<T> callBack) {
		okHttpHandler.post(new Runnable() {
			@Override
			public void run() {
				if (callBack != null) {
					callBack.onReqSuccess(action, result);
				}
			}
		});
	}

	/**
	 * 统一处理失败信息
	 * @param errorMsg
	 * @param callBack
	 * @param <T>
	 */
	private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
		okHttpHandler.post(new Runnable() {
			@Override
			public void run() {
				if (callBack != null) {
					callBack.onReqFailed(errorMsg);
				}
			}
		});
	}

}
