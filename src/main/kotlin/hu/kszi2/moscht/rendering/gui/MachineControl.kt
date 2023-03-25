package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MachineStatus
import hu.kszi2.moscht.MachineType

@Composable
private fun renderStatus(status: MachineStatus) {
    val (stat, _) = status.effectiveStatus()
    return when (stat) {
        MachineStatus.MachineStatusType.Available -> Icon(
            Icons.Filled.Check,
            "Available",
            tint = Color.Green
        )

        MachineStatus.MachineStatusType.InUse -> Icon(
            Icons.Filled.Close,
            "Used",
            tint = Color.Red
        )

        MachineStatus.MachineStatusType.Unknown -> Icon(
            Icons.Default.Info,
            "Unknown",
            tint = Color.LightGray
        )
    }
}

@Composable
private fun renderType(type: MachineType) {
    return when (type) {
        is MachineType.WashingMachine -> Text("Mosógép")
        is MachineType.Dryer -> Text("Szárítógép")
        is MachineType.Unknown -> Text("Ismeretlen(${type.shortName})")
    }
}

@Composable
fun machineControl(machine: Machine) {
    Box(
        Modifier
            .border(2.dp, Color(200, 200, 200))
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            renderType(machine.type)
            renderStatus(machine.status)
        }
    }
}