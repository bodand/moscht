package hu.kszi2.moscht.parsing

import hu.kszi2.moscht.Machine

interface MachineAsyncJsonParser {
    suspend fun parseJson(payload: String): List<Machine>
}

interface MachineJsonParser {
    fun parseJson(payload: String): List<Machine>
}
