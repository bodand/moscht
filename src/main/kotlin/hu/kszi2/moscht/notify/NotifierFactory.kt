package hu.kszi2.moscht.notify

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URL
import java.util.jar.Manifest
import kotlin.io.path.absolute
import kotlin.io.path.outputStream

object NotifierFactory {
    suspend fun load(): Notifier {
        val manifests = withContext(Dispatchers.IO) {
            javaClass.classLoader.getResources("META-INF/MANIFEST.MF")
        }
        val logger = LoggerFactory.getLogger(NotifierFactory::class.java)
        while (manifests.hasMoreElements()) {
            try {
                val manifest = withContext(Dispatchers.IO) {
                    Manifest(manifests.nextElement().openStream())
                }
                val notifier = manifest.mainAttributes.getValue("Moscht-Notification") ?: continue
                val notifierResource = javaClass.classLoader.getResource(notifier) ?: continue
                logger.warn("Using $notifier as native notifier implementation")

                val loadable = getLoadableNotifier(notifierResource)
                System.load(loadable.absolute().toString())
                return NativeNotifier()
            } catch (native: UnsatisfiedLinkError) {
                logger.error("loading native notifier failed")
                break
            } catch (io: IOException) {
                logger.warn(io.stackTraceToString())
            }
        }
        logger.error("cannot load native notifier class: notifications will be disabled")
        return DummyNotifier()
    }

    private suspend fun getLoadableNotifier(notifierResource: URL) =
        withContext(Dispatchers.IO) {
            notifierResource.openStream().use { inStrm ->
                val tmpFile = kotlin.io.path.createTempFile()
                tmpFile.outputStream().use { outStrm ->
                    inStrm.copyTo(outStrm)
                }
                tmpFile
            }
        }
}