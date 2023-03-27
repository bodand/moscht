package hu.kszi2.moscht

import hu.kszi2.moscht.rendering.GuiRenderer
import hu.kszi2.moscht.rendering.SimpleCliRenderer

suspend fun main(args: Array<String>) {
    val renderer =
        if (args.isNotEmpty() && args[0] == "-no-gui") {
            SimpleCliRenderer()
        } else {
            GuiRenderer()
        }

    val apiv1 = MosogepApiV1()
    val apiv2 = MosogepApiV2()
    renderer.renderData(apiv1, apiv2)
}
