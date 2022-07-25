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
                call.respond(
                    HttpStatusCode.OK,
                    message = wrapFailureInApiResponse<User>(-1, "No users found")
                )

            call.respond(
                HttpStatusCode.OK,
                message = wrapSuccessInApiResponse(userStorage)
            )
        }

        get("/{id}"){
            val id = call.parameters["id"] ?:
            return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = wrapFailureInApiResponse<User>(HttpStatusCode.BadRequest.value, "Missing Path variable (id)")
            )

            val user = userStorage.find { it.id == id } ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                message = wrapFailureInApiResponse<User>(HttpStatusCode.NotFound.value, "user not found")
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

            val tempCustomer = userStorage.find { it.id == customer.id } ?:
            return@put call.respond(
                status = HttpStatusCode.NotFound,
                message = wrapFailureInApiResponse<User>(HttpStatusCode.NotFound.value, "user not found by: " +
                        customer.id)
            )

            val updatedCustomer = tempCustomer
            updatedCustomer.firstName = customer.firstName
            updatedCustomer.lastName = customer.lastName
            updatedCustomer.email = customer.email

            Collections.replaceAll(userStorage, tempCustomer, updatedCustomer )
            call.respond(status = HttpStatusCode.OK, message = updatedCustomer)
        }

        delete("/{id}"){
            val id = call.parameters["id"] ?:
            return@delete call.respond(HttpStatusCode.BadRequest)

            if (userStorage.removeIf { it.id == id }){
                call.respond(
                    status = HttpStatusCode.OK,
                    message = wrapSuccessInApiResponse("user with $id deleted")
                )
            }else{
                call.respond(
                    status = HttpStatusCode.OK,
                    message = wrapSuccessInApiResponse("user with $id deleted")
                )
            }
        }
    }
}