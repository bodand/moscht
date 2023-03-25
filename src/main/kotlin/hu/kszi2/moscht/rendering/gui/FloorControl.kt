package hu.kszi2.moscht.rendering.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import hu.kszi2.moscht.Machine
import androidx.compose.ui.unit.dp

@Composable
fun floorControl(floor: List<Machine>) {
    assert(floor.isNotEmpty())

    val separatorColor = Color(100, 100, 100)
    Column(
        Modifier.width(300.dp)
    ) {
        Row(
            Modifier.width(280.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(
                Modifier.width(100.dp),
                thickness = 1.dp,
                color = separatorColor
            )
            Text(
                "${floor[0].level}.",
                Modifier.width(20.dp),
                textAlign = TextAlign.Center,
                color = separatorColor
            )
            Divider(
                Modifier.width(100.dp),
                thickness = 1.dp,
                color = separatorColor
            )
        }
        Column(
            Modifier
                .width(280.dp)
                .height(60.dp * floor.size)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            floor.forEach {
                machineControl(it)
            }
        }
    }
}