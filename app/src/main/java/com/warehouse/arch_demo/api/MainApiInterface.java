package com.warehouse.arch_demo.api;

import com.warehouse.arch_demo.bean.AlarmDto;
import com.warehouse.arch_demo.bean.InfoBody;
import com.warehouse.news.homefragment.beans.LoginForm;
import com.warehouse.news.homefragment.beans.LoginResponse;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MainApiInterface {
    //登录
    @FormUrlEncoded
    @Headers({"Authorization: Basic YW5hbm9wcy1jbGllbnQtdWFjOmFuYW5vcHNDbGllbnRTZWNyZXQ=", "Content-Type: application/x-www-form-urlencoded"})
    @POST("uac/auth/form")
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("imageCode") String imageCode,
                                    @Field("grant_type") String grantType,
                                    @Field("client_id") String client_id,
                                    @Field("client_secret") String client_secret,
                                    @Header("deviceId") Long deviceId
    );
   //登录养老
   @POST("auth/login/noCaptcha")
   Observable<LoginResponse> login1(@Body LoginForm loginForm);

    //获取用户信息
    @POST("uac/user/queryUserInfo/{loginName}")
    Observable<UserInformation> getUserInfo(@Path("loginName") String loginName, @Header("Authorization") String postToken);
    //获取组织信息
    @POST("uac/user/getUacUserById/{userId}")
    Observable<GroupIdResponse> getGroupId(@Path("userId")Long userId,@Header("Authorization") String postToken);
    //获取验证码图片
    @POST("uac/auth/code/image")
    Observable<PostResponse> getImage(@Header("deviceId") Long deviceId);
    //添加报警
    @POST("amc/alarm/save")
    Observable<PostResponse>addAlarm(@Body AlarmDto amcAlarm, @Header("token")String token);

    //获取用户信息
   @GET("infoManage/userInfo/get/{id}")
    Observable<InfoBody> getUserInfo1(@Path("id")String userId,@Header("token")String token);

}
