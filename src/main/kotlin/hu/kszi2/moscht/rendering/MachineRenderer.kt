package hu.kszi2.moscht.rendering

import hu.kszi2.moscht.Machine
import hu.kszi2.moscht.MosogepAsyncApi

interface MachineRenderer {
    suspend fun renderData(api: MosogepAsyncApi)
}