package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hu.kszi2.moscht.rendering.gui.filters.FloorFilter

@Composable
fun filterFloors(
    floorFilter: FloorFilter,
    setFloorFilter: (FloorFilter) -> Unit,
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
                "Szintek",
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
        Spacer(Modifier.height(16.dp))

        var floorsText by remember { mutableStateOf(TextFieldValue(floorFilter.floorsString)) }
        TextField(
            value = floorsText,
            { value ->
                floorsText = value
                setFloorFilter(FloorFilter(floorsText.text))
            }
        )
    }
}
