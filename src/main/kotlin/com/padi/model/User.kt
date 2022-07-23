package com.padi.model


@kotlinx.serialization.Serializable
data class User(val id: String) {
    //var id: String = ""
    var  firstName: String = ""
    var lastName: String = ""
    var email: String = ""
}

var userStorage = mutableListOf<User>()



