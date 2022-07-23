package com.padi.enums

enum class ResponseMessage (val code: Int, val message: String){
    SUCCESS(0,"Success"),
    FAILED(-1,"Failed"),
}