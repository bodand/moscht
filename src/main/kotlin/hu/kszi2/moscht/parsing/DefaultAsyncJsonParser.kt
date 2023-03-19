package hu.kszi2.moscht.parsing

import hu.kszi2.moscht.Machine
import kotlinx.coroutines.*

class DefaultAsyncJsonParser(private val blockingImpl: MachineJsonParser = DefaultJsonParser()) : MachineAsyncJsonParser {
    override suspend fun parseJson(payload: String): List<Machine> {
        return withContext(Dispatchers.Default) {
            return@withContext blockingImpl.parseJson(payload)
        }
    }
}
