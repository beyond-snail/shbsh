package com.landicorp.yinshang.data;

import com.landicorp.yinshang.data.response.LoginResponse;
import com.landicorp.yinshang.data.response.MemberResponse;
import com.landicorp.yinshang.data.response.SaleResponse;
import com.landicorp.yinshang.data.response.TransactionResponse;
import com.landicorp.yinshang.data.response.UndoResponse;
import com.landicorp.yinshang.data.response.WalletResponse;
import com.squareup.okhttp.RequestBody;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by u on 2017/1/9.
 */
public interface ApiService {
  /*  @GET("service/getIpInfo.php")
    Call<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);*/


//    @POST("api/index.php")
//    Observable<LoginResponse> login(@Body JsonObject jsonObject);

//    @FormUrlEncoded
//    @POST("api/index.php")
//    Observable<LoginResponse> login(@Field("cmd") String cmd, @Field("params") String data);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<LoginResponse> login(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<MemberResponse> getMemberInfo(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<SaleResponse> saleMoneyCalculate(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<TransactionResponse> uploadTransaction(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<WalletResponse> uploadWallet(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<UndoResponse> undo(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<TransactionResponse> reprint(@Body RequestBody jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/index.php")
    Observable<TransactionResponse> shift_room(@Body RequestBody jsonObject);

}
