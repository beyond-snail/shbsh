package com.landicorp.yinshang.data;

import android.content.Context;

import com.landicorp.yinshang.utils.Util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by u on 2017/1/9.
 */

public class RequestManager {

    private static volatile RequestManager mInstance;//单利引用
    private ApiService apiService;

    /**
     * 初始化RequestManager
     */
//    public RequestManager(Context context) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        apiService = retrofit.create(ApiService.class);
//    }

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
                    inst = new RequestManager();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public <T> void post(final Context context, Observable<T> observable, final ReqCallBack<T> callBack) {
        if(observable != null) {
            Subscriber<T> subscriber = new Subscriber<T>() {

                @Override
                public void onStart() {
                    super.onStart();
                    Util.showProgress(context);
                }

                @Override
                public void onNext(T result) {
                    callBack.onReqSuccess(result);
                }

                @Override
                public void onError(Throwable e) {
                    callBack.onReqFailed("请求失败");
                    Util.dismissProgress();
                }

                @Override
                public void onCompleted() {
                    Util.dismissProgress();
                }
            };
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }

    public <T> void postSpecial(final Context context, Observable<T> observable, final ReqCallBack<T> callBack) {
        if(observable != null) {
            Subscriber<T> subscriber = new Subscriber<T>() {

                @Override
                public void onStart() {
                    super.onStart();
                    Util.showSpecialProgress(context);
                }

                @Override
                public void onNext(T result) {
                    callBack.onReqSuccess(result);
                }

                @Override
                public void onError(Throwable e) {
                    callBack.onReqFailed("请求失败");
                    Util.dismissSpecialProgress();
                }

                @Override
                public void onCompleted() {
                    Util.dismissSpecialProgress();
                }
            };
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }


}
