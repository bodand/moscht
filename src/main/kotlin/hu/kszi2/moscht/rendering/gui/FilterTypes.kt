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
import hu.kszi2.moscht.MachineType
import hu.kszi2.moscht.filter.TypeFilter
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter
import hu.kszi2.moscht.rendering.gui.filters.TypeEntryFilter

@Composable
fun filterTypes(
    searchFields: EntryMapFilter<TypeEntryFilter>,
    setSearch: (EntryMapFilter<TypeEntryFilter>) -> Unit,
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
                "Típus",
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
            "Mosógép",
            MachineType.WashingMachine,
            searchFields,
            TypeEntryFilter.WashingMachineFilter,
            setSearch
        ) {
            TypeFilter(MachineType.WashingMachine)
        }
        filterCheckbox(
            "Szárító",
            MachineType.Dryer,
            searchFields,
            TypeEntryFilter.DryerFilter,
            setSearch
        ) {
            TypeFilter(MachineType.Dryer)
        }
    }
}