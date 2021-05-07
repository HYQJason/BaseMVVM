package com.xbmm.http.base

/**
 * @ClassName: BaseResponse
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/3/26 16:36
 */
open class BaseResponse<T>(val code: Int, val msg: String, var data: T) {
    fun isSuccess() = code == 0
}
