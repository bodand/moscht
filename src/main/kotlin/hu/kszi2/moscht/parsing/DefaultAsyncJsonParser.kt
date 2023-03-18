package hu.kszi2.moscht.parsing

import hu.kszi2.moscht.Machine

class DefaultAsyncJsonParser(val blockingImpl: MachineJsonParser) : MachineAsyncJsonParser {
    override suspend fun parseJson(payload: String): Machine {
        TODO("Not yet implemented")
    }
}
