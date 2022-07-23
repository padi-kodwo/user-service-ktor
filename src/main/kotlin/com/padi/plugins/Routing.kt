package com.padi.plugins

import com.padi.route.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    routing {
        userRouting()
    }
}
