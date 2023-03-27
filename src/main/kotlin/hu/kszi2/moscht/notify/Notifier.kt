package hu.kszi2.moscht.notify

interface Notifier {
    fun enable()
    fun disable()
    fun sendNotification(msg: String)
}