package hu.kszi2.moscht.rendering.gui.filters

import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.filter.MachineFilter

class FloorFilter(val floorsString: String) : MachineFilter {
    private val floors: Set<Int>

    init {
        floors = floorsString
            .filter { ch -> ch.isDigit() || ch == ',' }
            .split(',')
            .map { floorStr ->
                floorStr.toIntOrNull()
            }
            .filter { it != null }
            .map { it!! }
            .toHashSet()
    }

    override fun accept(machine: Machine): Boolean =
        if (floors.isEmpty()) true
        else floors.contains(machine.level)

    override fun reportChecked(thing: Any): Boolean = floors.isNotEmpty()
}