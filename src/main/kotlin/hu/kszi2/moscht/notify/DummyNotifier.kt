package hu.kszi2.moscht.notify

import org.slf4j.LoggerFactory

class DummyNotifier : Notifier {
    companion object {
        private val logger = LoggerFactory.getLogger(DummyNotifier::class.java)
    }

    override fun enable() {
        logger.info("called enable on DummyNotifier")
    }

    override fun disable() {
        logger.info("called disable on DummyNotifier")
    }

    override fun sendNotification(msg: String) {
        logger.info("sent notification to DummyNotifier")
    }
}