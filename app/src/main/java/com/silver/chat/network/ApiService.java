package com.silver.chat.network;

import com.silver.chat.network.responsebean.GroupBean;
import com.silver.chat.network.requestbean.JoinedGroupRequest;
import com.silver.chat.network.requestbean.LoginRequest;
import com.silver.chat.network.requestbean.RegisterRequest;
import com.silver.chat.network.responsebean.BaseResponse;
import com.silver.chat.network.responsebean.ContactListBean;
import com.silver.chat.network.requestbean.CreatGroupBean;
import com.silver.chat.network.responsebean.FriendInfo;
import com.silver.chat.network.responsebean.LoginRequestBean;
import com.silver.chat.network.responsebean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by joe on 2017/4/26.
 * 定义当前项目的所有业务逻辑的请求方法
 */

public interface ApiService {

    //验证手机是否注册
    @GET("imx/{version}/user/account/{mobile}")
    Call<BaseResponse> checkPhoneCode(@Path("version") String version, @Path("mobile") String phone);

    //获取验证码
    @GET("imx/{version}/sms/{phone}/{genre}")
    Call<BaseResponse> registCode(@Path("version") String version, @Path("phone") String phone, @Path("genre") int genre);

    //注册
    @POST("imx/{version}/user/register")
    Call<BaseResponse> registPhone(@Path("version") String version, @Body RegisterRequest registerRequest);

    //登录
    @POST("imx/{version}/user/login")
    Call<BaseResponse<LoginRequestBean>> goLogin(@Path("version") String version, @Body LoginRequest loginRequest);

    //获取用户信息
    @GET("imx/{version}/user/information")
    Call<BaseResponse<UserInfoBean>> userInfo(@Path("version") String version, @Header("token") String token);

    //获取好友列表
    @GET("imx/{version}/userfriend/{userId}/{page}/{count}")
    Call<BaseResponse<ArrayList<ContactListBean>>> contactList( @Path("version") String version, @Path("userId") String userId, @Path("page") String page, @Path("count") String count,@Header("token")String token);

    //创建群组
    @POST("imx/{version}/user/group")
    Call<BaseResponse<CreatGroupBean>> creatgroup(@Path("version") String version, @Header("token") String token, @Body CreatGroupBean creatGroupBean);

    //获取已经加入的群信息列表
    @POST("imx/{version}/user/group/addgrouplist")
    Call<BaseResponse<ArrayList<GroupBean>>> joinedGroupList(@Path("version")String version, @Body JoinedGroupRequest joinedGroupRequest, @Header("token")String token);

    //创建讨论组
    @POST("imx/{version}/user/discugroup/creatediscugroup")
    Call<BaseResponse<CreatGroupBean>> creatdiscugroup(@Path("version") String version, @Header("token") String token, @Body CreatGroupBean creatGroupBean);

    //获取好友信息
    @GET("imx/{version}/friendinfo/{userId}/{friendId}")
    Call<BaseResponse<List<FriendInfo>>> friendinfo(@Header("token") String token, @Path("version") String version, @Path("userId") String userId, @Path("friendId") String friendId);

    //申请添加好友
    @POST("imx/leaf/friend/{userId}/{friendId}/{comment}")
    Call<BaseResponse>addFriend(@Path("userId")String userId,@Path("friendId")String friendId,@Path("comment")String comment,@Header("token")String token);

}
