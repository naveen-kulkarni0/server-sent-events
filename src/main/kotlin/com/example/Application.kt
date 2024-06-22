package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.Routing.Plugin.install
import io.ktor.server.sse.*

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8080, // This is the port on which Ktor is listening
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureRouting()
}
