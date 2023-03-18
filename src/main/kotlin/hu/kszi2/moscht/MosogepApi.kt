package hu.kszi2.moscht

interface MosogepAsyncApi {
    suspend fun loadMachines(): List<Machine>
}

interface MosogepApi {
    fun loadMachines(): List<Machine>
}
