package app.sample.controller

import app.sample.integration.SendingMessage
import app.sample.services.SimpleResponse
import io.quarkus.security.identity.SecurityIdentity
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext
import kotlinx.serialization.Serializable


@Path("/app")
class AppController(
    private val service: SimpleResponse,
    private val s: SendingMessage,
    private val security: SecurityIdentity
) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun index() = s.sendMessage("message from controller")

    @GET
    @Path("/me")
    @RolesAllowed("\${user-roles}")
    fun user(): User {
        return User(security.principal.name)
    }
}

@Serializable
class User(
    val userName: String
)