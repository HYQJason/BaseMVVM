package com.hx.ice.net.service

import com.hx.ice.act.login.bean.LoginBean
import com.hx.ice.act.main.bean.UserInfo
import com.xbmm.http.base.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.*

/**
 * @ClassName: IceService
 * @Description:@HeaderMap  headers:Map<String, String>
 * @Author: WY-HX
 * @Date: 2021/3/26 17:17
 */
interface IceService {

    @POST("article/")
    suspend fun get(): BaseResponse<UserInfo>

    @GET("/user/app/user/v1/sms/code")
    suspend fun sendCode(@Query("areaCode") areaCode: String, @Query("mobile") mobile: String, @Query("type") type: Int): BaseResponse<Boolean>

    @POST("/user/app/user/v1/login")
    suspend fun login(@Body requestBody: RequestBody): BaseResponse<LoginBean>
}