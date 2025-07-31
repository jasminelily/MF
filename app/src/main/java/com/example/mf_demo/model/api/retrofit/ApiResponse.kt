package com.example.mf_demo.model.api.retrofit

import com.example.mf_demo.model.api.base.ApiResult
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess


suspend fun <T> ApiResponse<T>.toApiResult(): ApiResult<T> {
    var result: ApiResult<T> = ApiResult.Error("Unhandled error")

    this.suspendOnSuccess {
        result = ApiResult.Success(data)
    }.suspendOnError {
        // API error (non-2xx response)
        result = ApiResult.Error(message(), statusCode.code)
    }.suspendOnException {
        result = ApiResult.Error(message ?: "Unexpected error")
    }

    return result
}
