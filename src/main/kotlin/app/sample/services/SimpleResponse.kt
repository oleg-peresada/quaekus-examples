package app.sample.services

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SimpleResponse {
    fun responseText() = "Hello World!"
}