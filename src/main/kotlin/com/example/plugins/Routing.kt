package com.example.plugins

import com.github.javafaker.Faker
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.delay
import kotlinx.html.*

fun Application.configureRouting() {
    install(SSE)
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"SSE Example" }
                    style {
                        unsafe {
                            raw(
                                """
                                    body {
                                        font-family: Arial, sans-serif;
                                        background-color: #f0f0f0;
                                        padding: 20px;
                                    }
                                    .container {
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #fff;
                                        border-radius: 8px;
                                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                                        padding: 20px;
                                    }
                                    h1 {
                                        text-align: center;
                                        color: #333;
                                    }
                                    button {
                                        padding: 10px 20px;
                                        font-size: 16px;
                                        background-color: #007bff;
                                        color: #fff;
                                        border: none;
                                        border-radius: 4px;
                                        cursor: pointer;
                                    }
                                    button:hover {
                                        background-color: #0056b3;
                                    }
                                    #eventsContainer {
                                        margin-top: 20px;
                                        padding: 10px;
                                        background-color: #f9f9f9;
                                        border: 1px solid #ddd;
                                        border-radius: 4px;
                                        max-height: 300px;
                                        overflow-y: auto;
                                    }
                                    """
                            )
                        }
                    }
                }
                body {
                    div(classes = "container") {
                        h1 { +"Server-Sent Events Example" }
                        button {
                            attributes["onclick"] = "startSSE()"
                            +"Start SSE"
                        }
                        div {
                            id = "eventsContainer"
                        }
                        script {
                            unsafe {
                                raw(
                                    """
                                        function startSSE() {
                                            const eventsContainer = document.getElementById('eventsContainer');
                                            eventsContainer.innerHTML = ''; // Clear previous content
                                            
                                            const eventSource = new EventSource('/events');
                                            
                                            eventSource.onmessage = function(event) {
                                                const newEvent = document.createElement('div');
                                                newEvent.textContent = event.data;
                                                eventsContainer.appendChild(newEvent);
                                            };
                                            
                                            eventSource.onerror = function(event) {
                                                console.error('EventSource failed:', event);
                                                eventSource.close();
                                            };
                                        }
                                        """
                                )
                            }
                        }
                    }
                }
            }
        }

        sse("/events") {
            val faker = Faker()
            repeat(100){
                send(ServerSentEvent("Caught ${faker.pokemon().name()}"))
                delay(100)
            }
        }
    }
}
