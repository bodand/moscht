package hu.kszi2.moscht.parsing

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MachineStatus
import hu.kszi2.moscht.MachineStatus.*
import hu.kszi2.moscht.MachineType
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class DefaultJsonParser(val unknownThreshold: Int = 3) : MachineJsonParser {
    @Serializable
    private data class Level(val id: Int, val machines: List<InternalMachine>, val last_query_time: String)

    @Serializable
    private data class InternalMachine(val id: Int, val type: String, val status: Int, val message: String?)

    override fun parseJson(payload: String): List<Machine> {
        val internals = Json.decodeFromString<List<Level>>(payload)
        return internals.flatMap { lvl ->
            lvl.machines.map { machine ->
                Machine(lvl.id, parseTypeShortString(machine.type), parseStatus(machine.status, lvl.last_query_time))
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

    private fun parseStatus(returnedStatus: Int, queryTime: String): MachineStatus {
        val statType = when (returnedStatus) {
            0 -> MachineStatusType.Available
            1 -> MachineStatusType.InUse
            else -> MachineStatusType.Unknown
        }
        return MachineStatus(statType, Instant.parse(queryTime))
    }
}
