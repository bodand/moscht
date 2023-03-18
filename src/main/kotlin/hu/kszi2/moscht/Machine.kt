package hu.kszi2.moscht

import java.time.Instant
import java.util.*

enum class MachineStatus {
    Available,
    InUse,
    Unknown;

    val since: Date = Date.from(Instant.EPOCH)
}

sealed class MachineType {
    object WashingMachine : MachineType()
    object Dryer : MachineType()
    data class Unknown(val shortName: String) : MachineType()
}

data class Machine(val level: Int, val type: MachineType, var status: MachineStatus)
