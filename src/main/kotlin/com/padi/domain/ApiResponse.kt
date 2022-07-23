package com.padi.domain

@kotlinx.serialization.Serializable
data class ApiResponse<T>(val code: Int, val message: String, val data: T?){
    var error: BaseError? = null
}
