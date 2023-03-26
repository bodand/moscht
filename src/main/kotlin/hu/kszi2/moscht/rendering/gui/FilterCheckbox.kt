package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import hu.kszi2.moscht.filter.MachineFilter
import hu.kszi2.moscht.rendering.gui.filters.EntryMapFilter

@Composable
fun <E : Enum<E>> filterCheckbox(
    text: String,
    reportArg: Any,
    filter: EntryMapFilter<E>,
    entry: E,
    setter: (EntryMapFilter<E>) -> Unit,
    factory: () -> MachineFilter,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = filter.reportChecked(reportArg),
            onCheckedChange = { stat ->
                if (stat) {
                    setter(
                        filter.enable(
                            entry,
                            factory()
                        )
                    )
                } else {
                    setter(filter.disable(entry))
                }
            }
        )
        Text(text)
    }
}
