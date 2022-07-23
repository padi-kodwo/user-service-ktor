package com.padi.domain

@kotlinx.serialization.Serializable
data class BaseError(val code: Int, val message: String, val url: String)

