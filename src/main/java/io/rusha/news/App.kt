package io.rusha.news

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@Serializable
data class New(
    val id: Int,
    val creationTime: String,
    val title: String,
    val text: String
)

@Serializable
data class AddNew(
    val title: String,
    val text: String
) {
    fun toNew(): New {
        return New(
            id = idGenerator.incrementAndGet(),
            creationTime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date()),
            title,
            text
        )
    }

    companion object {
        private val idGenerator = AtomicInteger(0)
    }
}

fun main() {
    val news = mutableListOf<New>()

    embeddedServer(Netty, port = 8000) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            get("/") {
                call.respond(news)
            }
            patch("/add") {
                val addNew = call.receive<AddNew>()
                val new = addNew.toNew()
                news.add(new)
                call.respondText("")
            }
            put("/{id}") {
                val id: Int by call.parameters
                val addNew = call.receive<AddNew>()
                val new = addNew.toNew()
                news.replaceAll { if (it.id == id) new else it }
                call.respondText("")
            }
            delete("/{id}") {
                val id: Int by call.parameters
                news.removeAll { it.id == id }
                call.respondText("")
            }
        }
    }.start(wait = true)
}