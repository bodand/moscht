package hu.kszi2.moscht.notify

class NativeNotifier : Notifier {
    external override fun enable()
    external override fun disable()
    external override fun sendNotification(msg: String)
}