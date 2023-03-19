package hu.kszi2.moscht

import java.time.Instant
import java.time.temporal.ChronoUnit

class MachineStatus(val status: MachineStatusType, val lastStatus: Instant) {
    enum class MachineStatusType {
        Available, InUse, Unknown
    }

    fun effectiveStatus(unknownThreshold: Int = 2): MachineStatusType {
        val now = Instant.now()
        val diff = ChronoUnit.HOURS.between(lastStatus, now)
        if (diff > unknownThreshold) return MachineStatusType.Unknown
        return status
    }
}

sealed class MachineType {
    object WashingMachine : MachineType()
    object Dryer : MachineType()
    data class Unknown(val shortName: String) : MachineType()
}

data class Machine(val level: Int, val type: MachineType, var status: MachineStatus)
