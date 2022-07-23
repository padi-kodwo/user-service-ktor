package com.padi.util

import com.padi.domain.ApiResponse
import com.padi.domain.BaseError
import com.padi.enums.ResponseMessage

class Utils {
}

fun <T> wrapSuccessInApiResponse(data: T): ApiResponse<T> {
    return ApiResponse(ResponseMessage.SUCCESS.code, ResponseMessage.SUCCESS.message, data)
}

fun <T> wrapFailureInApiResponse(code: Int, message: String): ApiResponse<T> {
    val baseError = BaseError(code, message, "")

    val apiResponse = ApiResponse<T>(ResponseMessage.FAILED.code, ResponseMessage.FAILED.message, null)
    apiResponse.error = baseError

    return apiResponse
}