package hu.kszi2.moscht

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import hu.kszi2.moscht.rendering.gui.floorControl
import kotlinx.coroutines.launch

import hu.kszi2.moscht.MachineStore.*

@Composable
fun loadingCircle() {
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
fun machinesView(
    api: MosogepAsyncApi,
    searchFields: SearchFields
) {
    val scope = rememberCoroutineScope()
    val (machines, setMachines) = remember { mutableStateOf(MachineStore()) }
    val verticalScroll = rememberScrollState(0)

    scope.launch {
        setMachines(machines.reloadFromApi(api))
    }

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

@Composable
fun filterStatus(
    searchFields: SearchFields,
    setSearch: (SearchFields) -> Unit
) {
    Column(Modifier.padding(8.dp)) {
        Text("Állapot: ")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.status == null,
                onClick = {
                    setSearch(searchFields.copy(status = null))
                }
            )
            Text("Minden állapot")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.status != null && searchFields.status.status == MachineStatus.MachineStatusType.Available,
                onClick = {
                    setSearch(
                        searchFields.copy(
                            status = MachineStatus(MachineStatus.MachineStatusType.Available)
                        )
                    )
                }
            )
            Text("Elérhető")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.status != null && searchFields.status.status == MachineStatus.MachineStatusType.InUse,
                onClick = {
                    setSearch(
                        searchFields.copy(
                            status = MachineStatus(MachineStatus.MachineStatusType.InUse)
                        )
                    )
                }
            )
            Text("Foglalt")
        }
    }
}

@Composable
fun filterTypes(
    searchFields: SearchFields,
    setSearch: (SearchFields) -> Unit
) {
    Column(Modifier.padding(8.dp)) {
        Text("Típus: ")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.type == null,
                onClick = {
                    setSearch(searchFields.copy(type = null))
                }
            )
            Text("Minden típus")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.type != null && searchFields.type == MachineType.WashingMachine,
                onClick = {
                    setSearch(searchFields.copy(type = MachineType.WashingMachine))
                }
            )
            Text("Mosógép")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = searchFields.type != null && searchFields.type == MachineType.Dryer,
                onClick = {
                    setSearch(searchFields.copy(type = MachineType.Dryer))
                }
            )
            Text("Szárítógép")
        }
    }
}

fun main() = application {
    val api = MosogepApiV1()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Moscht",
        state = rememberWindowState(width = 600.dp, height = 800.dp),
        resizable = false
    ) {
        val (filter, setFilter) = remember {
            mutableStateOf(
                SearchFields(
                    null,
                    null,
                    MachineStatus(MachineStatus.MachineStatusType.Available)
                )
            )
        }
        Row {
            Column(Modifier.width(300.dp)) {
                filterTypes(filter, setFilter)
                filterStatus(filter, setFilter)
            }
            machinesView(api, filter)
        }
    }
}
