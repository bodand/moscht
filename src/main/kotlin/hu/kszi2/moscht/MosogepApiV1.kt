package hu.kszi2.moscht

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MosogepApiV1(val blockingImpl: MosogepApi) : MosogepAsyncApi {
    override suspend fun loadMachines(): List<Machine> {
        return withContext(Dispatchers.IO) {
            blockingImpl.loadMachines()
        }
    }
}
