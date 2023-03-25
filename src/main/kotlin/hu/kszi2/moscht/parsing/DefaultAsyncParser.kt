package hu.kszi2.moscht.parsing

import hu.kszi2.moscht.Machine
import kotlinx.coroutines.*

class DefaultAsyncParser(private val blockingImpl: MachineParser = DefaultJsonParser()) : MachineAsyncParser {
    override suspend fun parse(payload: String): List<Machine> {
        return withContext(Dispatchers.Default) {
            blockingImpl.parse(payload)
        }
    }
}
