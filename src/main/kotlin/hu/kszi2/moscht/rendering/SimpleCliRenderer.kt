package hu.kszi2.moscht.rendering

import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MachineStatus
import hu.kszi2.moscht.MachineType
import hu.kszi2.moscht.MosogepAsyncApi

class SimpleCliRenderer : MachineRenderer {
    override suspend fun renderData(api: MosogepAsyncApi) {
        api.loadMachines().forEach(::renderMachine)
    }

    private fun renderMachine(machine: Machine) {
        println("${machine.level}. szinti ${renderType(machine.type)}: ${renderStatus(machine.status)}")
    }

    private fun renderStatus(status: MachineStatus): String {
        val (stat, since) = status.effectiveStatus()
        return when (stat) {
            MachineStatus.MachineStatusType.Available -> "Szabad"
            MachineStatus.MachineStatusType.InUse -> "Használatban"
            MachineStatus.MachineStatusType.Unknown -> "Ismeretlen(${since} órája)"
        }
    }

    private fun renderType(type: MachineType): String {
        return when (type) {
            is MachineType.WashingMachine -> "Mosógép"
            is MachineType.Dryer -> "Szárítógép"
            is MachineType.Unknown -> "Ismeretlen(${type.shortName})"
        }
    }
}