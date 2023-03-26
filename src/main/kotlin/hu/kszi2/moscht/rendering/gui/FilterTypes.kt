package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.kszi2.moscht.MachineType
import hu.kszi2.moscht.filter.TypeFilter
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter
import hu.kszi2.moscht.rendering.gui.filters.TypeEntryFilter

@Composable
fun filterTypes(
    searchFields: EntryMapFilter<TypeEntryFilter>,
    setSearch: (EntryMapFilter<TypeEntryFilter>) -> Unit
) {
    Column(Modifier.padding(8.dp)) {
        Text("Típus:")
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