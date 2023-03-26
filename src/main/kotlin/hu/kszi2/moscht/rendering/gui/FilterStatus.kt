package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hu.kszi2.moscht.MachineStatus
import hu.kszi2.moscht.filter.StatusFilter
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter
import hu.kszi2.moscht.rendering.gui.filters.StatusEntryFilter

@Composable
fun filterStatus(
    searchFields: EntryMapFilter<StatusEntryFilter>,
    setSearch: (EntryMapFilter<StatusEntryFilter>) -> Unit,
) {
    Column(Modifier.padding(8.dp)) {
        val separatorColor = Color(100, 100, 100)
        Row(
            Modifier.width(280.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(
                Modifier.width(80.dp),
                thickness = 1.dp,
                color = separatorColor
            )
            Text(
                "Státusz",
                Modifier.width(50.dp),
                textAlign = TextAlign.Center,
                color = separatorColor
            )
            Divider(
                Modifier.width(80.dp),
                thickness = 1.dp,
                color = separatorColor
            )
        }

        filterCheckbox(
            "Szabad",
            MachineStatus.MachineStatusType.Available,
            searchFields,
            StatusEntryFilter.AvailableFilter,
            setSearch
        ) {
            StatusFilter(MachineStatus(MachineStatus.MachineStatusType.Available))
        }
        filterCheckbox(
            "Foglalt",
            MachineStatus.MachineStatusType.InUse,
            searchFields,
            StatusEntryFilter.InUseFilter,
            setSearch
        ) {
            StatusFilter(MachineStatus(MachineStatus.MachineStatusType.InUse))
        }
        filterCheckbox(
            "Ismeretlen",
            MachineStatus.MachineStatusType.Unknown,
            searchFields,
            StatusEntryFilter.UnknownFilter,
            setSearch
        ) {
            StatusFilter(MachineStatus(MachineStatus.MachineStatusType.Unknown))
        }
    }
}