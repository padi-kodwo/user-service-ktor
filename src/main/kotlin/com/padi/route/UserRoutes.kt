package com.padi.route

import com.padi.model.User
import com.padi.model.userStorage
import com.padi.util.wrapFailureInApiResponse
import com.padi.util.wrapSuccessInApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRouting(){

    route("/users"){

        get {

            if (userStorage.isEmpty())
                call.respond(HttpStatusCode.OK,
                    message = wrapFailureInApiResponse<User>(-1, "No user found"))


            val apiResponse = wrapSuccessInApiResponse(userStorage)
            call.respond(HttpStatusCode.OK, message = apiResponse)
        }

        get("/{id}"){
            val id = call.parameters["id"] ?:return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val user = userStorage.find { it.id == id } ?: return@get call.respondText(
                "No user found",
                status = HttpStatusCode.NotFound
            )

            call.respond(user)
        }

        post {
            val user = call.receive<User>()
            userStorage.add(user)

            val apiResponse = wrapSuccessInApiResponse(user)
            call.respond(HttpStatusCode.Created, message = apiResponse)
        }

        put {
            val customer = call.receive<User>()

            val tempCustomer = userStorage.find { it.id == customer.id } ?: return@put call.respondText(
                "No user found",
                status = HttpStatusCode.BadRequest)

            val updatedCustomer = tempCustomer
            updatedCustomer.firstName = customer.firstName
            updatedCustomer.lastName = customer.lastName
            updatedCustomer.email = customer.email

            Collections.replaceAll(userStorage, tempCustomer, updatedCustomer )
            call.respond(HttpStatusCode.OK, message = updatedCustomer)
        }

        delete("/id"){
            //
        }
    }
}