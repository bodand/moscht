package hu.kszi2.moscht.rendering

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import hu.kszi2.moscht.*
import hu.kszi2.moscht.filter.ConjunctionFilter
import hu.kszi2.moscht.rendering.gui.*
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter
import hu.kszi2.moscht.rendering.gui.filters.StatusEntryFilter
import hu.kszi2.moscht.rendering.gui.filters.TypeEntryFilter
import hu.kszi2.moscht.rendering.gui.filterStatus
import hu.kszi2.moscht.rendering.gui.filterTypes
import hu.kszi2.moscht.rendering.gui.filters.FloorFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GuiRenderer : MachineRenderer {
    override suspend fun renderData(api: MosogepAsyncApi) {
        val initialMachines = api.loadMachines()
        application {
            val scope = rememberCoroutineScope()
            val (machines, setMachines) = remember {
                mutableStateOf(MachineStore(initialMachines))
            }

            scope.launch(Dispatchers.IO) {
                delay(10000)
                setMachines(machines.reloadFromApi(api))
            }

            Window(
                onCloseRequest = ::exitApplication,
                title = "Moscht",
                state = rememberWindowState(width = 600.dp, height = 800.dp),
                resizable = false
            ) {
                val (statusFilter, setStatusFilter) = remember {
                    mutableStateOf(EntryMapFilter.makeFilter<StatusEntryFilter>())
                }
                val (typeFilter, setTypeFilter) = remember {
                    mutableStateOf(EntryMapFilter.makeFilter<TypeEntryFilter>())
                }
                val (floorsFilter, setFloorsFilter) = remember {
                    mutableStateOf(FloorFilter(""))
                }

                Row {
                    Column(Modifier.width(300.dp)) {
                        filterFloors(floorsFilter, setFloorsFilter)
                        filterTypes(typeFilter, setTypeFilter)
                        filterStatus(statusFilter, setStatusFilter)
                    }
                    machinesListView(machines, ConjunctionFilter(floorsFilter, statusFilter, typeFilter))
                }
            }
        }
    }
}