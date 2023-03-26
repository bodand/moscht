package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MachineStore
import hu.kszi2.moscht.filter.MachineFilter

@Composable
private fun loadingCircle() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Betöltés...")
    }
}

private fun splitByFloor(machines: List<Machine>): List<List<Machine>> {
    val res = mutableMapOf<Int, List<Machine>>()
    machines.forEach { machine ->
        if (res.containsKey(machine.level)) {
            res[machine.level] = res[machine.level]!!.plus(machine)
        } else {
            res[machine.level] = listOf(machine)
        }
    }
    return res.values.toList()
}

@Composable
fun machinesListView(
    machines: MachineStore,
    searchFields: MachineFilter
) {
    val verticalScroll = rememberScrollState(0)

    Box(
        Modifier.width(300.dp)
    ) {
        Column(
            Modifier
                .width(300.dp)
                .verticalScroll(verticalScroll)
        ) {
            if (machines.isEmpty()) {
                loadingCircle()
            } else {
                val floors = splitByFloor(machines.listMachines(searchFields))
                floors.map {
                    floorControl(it)
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(verticalScroll),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        )
    }
}
