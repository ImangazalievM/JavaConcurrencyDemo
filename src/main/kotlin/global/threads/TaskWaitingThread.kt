package global.threads

class TaskWaitingThread(
    private val wait: () -> Unit
) : Thread() {

    override fun run() {
        wait()
    }
}