package com.example.hongcheng.common.retrofitHelper;

import com.example.hongcheng.common.constant.HttpConstants;
import com.example.hongcheng.common.retrofitHelper.response.UserInfoResponse;
import com.example.hongcheng.common.retrofitHelper.response.VersionInfoResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hongcheng on 16/3/30.
 */
public interface ExampleRetrofit {

    @POST(HttpConstants.LOGIN_URL)
    Observable<UserInfoResponse> login(@Query("username") String username, @Query("password") String password);

    @POST(HttpConstants.LOGIN_URL)
    Observable<UserInfoResponse> login(@Query("token") long token, @Query("accountId") long accountId);

    @POST(HttpConstants.QUERY_VERSION_URL)
    Observable<VersionInfoResponse> queryVersion(@Query("token") long token, @Query("accountId") long accountId);

    @GET("/files/{fileName}")
    @Headers({"Content-Type: image/jpeg"})
    Response downloadNewVersion(@Path("fileName") String fileName);
}
