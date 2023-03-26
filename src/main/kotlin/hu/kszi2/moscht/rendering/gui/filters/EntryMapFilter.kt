package hu.kszi2.moscht.rendering.gui.filters

import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.filter.MachineFilter

class EntryMapFilter<E : Enum<E>> private constructor(map: Map<E, MachineFilter?>) : MachineFilter {
    companion object {
        inline fun <reified E : Enum<E>> makeFilter() = EntryMapFilter(*enumValues<E>())

    }

    private val mapping: Map<E, MachineFilter?> = map

    constructor(vararg entries: E)
            : this(entries.fold(HashMap()) { acc, entry ->
        acc[entry] = null
        acc
    })

    fun <F : E> disable(entry: F) = modifyEntry(entry, null)

    fun <F : E> enable(entry: F, filter: MachineFilter) = modifyEntry(entry, filter)

    private fun <F : E> modifyEntry(entry: F, filter: MachineFilter?): EntryMapFilter<E> {
        val ret = mapping.toMutableMap()
        ret[entry] = filter
        return EntryMapFilter(ret)
    }

    override fun accept(machine: Machine): Boolean {
        var allNull = true
        var acceptStat = false
        mapping.forEach { (_, filter) ->
            if (filter == null) return@forEach
            allNull = false
            acceptStat = acceptStat || filter.accept(machine)
        }
        return allNull || acceptStat
    }

    override fun reportChecked(thing: Any): Boolean =
        mapping
            .map { (_, filter) -> filter?.reportChecked(thing) ?: false }
            .fold(false) { acc, stat -> acc || stat }
}
