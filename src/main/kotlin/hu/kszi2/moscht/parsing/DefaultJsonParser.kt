package hu.kszi2.moscht.parsing

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MachineStatus
import hu.kszi2.moscht.MachineType

class DefaultJsonParser : MachineJsonParser {
    @Serializable
    private data class Level(val id: Int, val machines: List<InternalMachine>, val last_query_time: String)

    @Serializable
    private data class InternalMachine(val id: Int, val type: String, val status: Int, val message: String?)

    @Serializable
    private data class Response(val levels: List<Level>)

    override fun parseJson(payload: String): List<Machine> {
        val internals = Json.decodeFromString<Response>(payload)
        return internals.levels.flatMap { lvl ->
            lvl.machines.map { machine ->
                Machine(lvl.id, parseTypeShortString(machine.type), MachineStatus.Available)
            }
        }
    }

    private fun parseTypeShortString(shortType: String): MachineType {
        return when (shortType) {
            "WS" -> MachineType.WashingMachine
            "DR" -> MachineType.Dryer
            else -> MachineType.Unknown(shortType)
        }
    }
}
