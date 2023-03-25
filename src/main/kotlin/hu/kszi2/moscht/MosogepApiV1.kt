package hu.kszi2.moscht

import hu.kszi2.moscht.parsing.DefaultAsyncParser
import hu.kszi2.moscht.parsing.MachineAsyncParser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*

class MosogepApiV1(private val parser: MachineAsyncParser = DefaultAsyncParser()) : MosogepAsyncApi {
    private val webClient = HttpClient(CIO)

    override suspend fun loadMachines(): List<Machine> {
        val status = webClient.get("https://mosogep.sch.bme.hu/api/v1/laundry-room/")
        if (status.status != HttpStatusCode.OK) {
            throw UnreachableApiError()
        }

        val body: String = status.body()
        return parser.parse(body)
    }

    class UnreachableApiError : Throwable()
}
