package hu.kszi2.moscht

class MachineStore(private val machines: List<Machine> = listOf()) {
    suspend fun reloadFromApi(api: MosogepAsyncApi) = MachineStore(api.loadMachines())

    data class SearchFields(
        val floor: Int?,
        val type: MachineType?,
        val status: MachineStatus?
    ) {
        companion object {
            val matchAll = SearchFields(null, null, null)
        }
    }

    fun listMachines(searchFields: SearchFields = SearchFields.matchAll): List<Machine> {
        if (searchFields === SearchFields.matchAll) return machines
        return machines.filter { machine ->
            val statEq = searchFields.status?.equals(machine.status)
            if (statEq != null && !statEq) return@filter false

            val typeEq = searchFields.type?.equals(machine.type)
            if (typeEq != null && !typeEq) return@filter false

            val levelEq = searchFields.floor?.equals(machine.level)
            if (levelEq != null && !levelEq) return@filter false

            true
        }
    }

    fun isEmpty(): Boolean {
        return machines.isEmpty()
    }
}
