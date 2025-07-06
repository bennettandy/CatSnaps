package com.avsoftware.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine, apiKey: String): HttpClient {
    return HttpClient(engine) {
        // install plugins
        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            //configure kotlinx serialization
            json(
                json = Json {
                    // don't crash on unknowns
                    ignoreUnknownKeys = true
                }
            )
        }

        defaultRequest {
            headers.append("x-api-key", apiKey)
        }

//        install(Auth){
//            // todo implement auth if needed
//            refeshTokens {
//
//            }
//        }
    }
}