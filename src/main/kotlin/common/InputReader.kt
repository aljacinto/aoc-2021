package common

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

class InputReader {
    companion object {
        suspend fun readInput(path: String) = with(HttpClient(CIO)) {
            this.get<String> {
                url("https://adventofcode.com/2021/${path}")
                header("cookie", "session=" + System.getenv().get("COOKIE"))
            }
        }
    }
}