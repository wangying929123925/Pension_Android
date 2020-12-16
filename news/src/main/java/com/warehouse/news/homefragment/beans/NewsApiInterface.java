package com.warehouse.news.homefragment.beans;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import io.reactivex.Observable;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface NewsApiInterface {
    @GET("release/news")
    Observable<NewsListBean> getNewsList(@Query("channelId") String channelId,
                                         @Query("channelName") String channelName,
                                         @Query("page") String page);

    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels();

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

    //获取消息列表
    @POST("websocket/websocket/queryWebsocketMsgInfo")
    Observable<MessageListResponse> getMessageList(@Body MessageListRequest queryWebsocketMsgInfo, @Header("Authorization") String postToken);
    //获取工单列表
    @GET("taskManage/taskInfo/list/{serverId}")
    Observable<OrderListResponse> getAlarmOrderList(@Path("serverId") String serverId, @Header("Authorization") String postToken);

}
