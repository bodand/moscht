package hu.kszi2.moscht.rendering

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import hu.kszi2.moscht.*
import hu.kszi2.moscht.filter.ConjunctionFilter
import hu.kszi2.moscht.notify.DummyNotifier
import hu.kszi2.moscht.notify.Notifier
import hu.kszi2.moscht.notify.NotifierFactory
import hu.kszi2.moscht.rendering.gui.*
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter
import hu.kszi2.moscht.rendering.gui.filters.StatusEntryFilter
import hu.kszi2.moscht.rendering.gui.filters.TypeEntryFilter
import hu.kszi2.moscht.rendering.gui.filterStatus
import hu.kszi2.moscht.rendering.gui.filterTypes
import hu.kszi2.moscht.rendering.gui.filters.FloorFilter
import org.slf4j.LoggerFactory

class GuiRenderer : MachineRenderer {
    private suspend fun loadApis(apis: Array<out MosogepAsyncApi>): List<Machine> {
        for (api in apis) {
            try {
                return api.loadMachines()
            } catch (ex: MosogepAsyncApi.UnreachableApiError) {
                LoggerFactory.getLogger(GuiRenderer::class.java).error(
                    ex.stackTraceToString()
                )
            }
        }
        return emptyList()
    }

    override suspend fun renderData(vararg apis: MosogepAsyncApi) {
        application {
            val (notifier, setNotifier) = remember {
                mutableStateOf<Notifier>(DummyNotifier())
            }
            val (machines, setMachines) = remember {
                mutableStateOf(MachineStore())
            }

            LaunchedEffect(key1 = Unit) {
                val notif = NotifierFactory.load()
                notif.enable()
                setNotifier(notif)

                val loaded = loadApis(apis)
                setMachines(MachineStore(loaded))
                notif.sendNotification("asdasd")
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