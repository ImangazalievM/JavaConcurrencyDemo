package windows.other.threadlocal

import threads.TaskProgress

class ThreadLocalTask(
    private val threadLocal: ThreadLocal<Int>,
    val taskNumber: Int,
    val max: Int,
    private val onProgress: () -> Unit
) : Thread() {

    override fun run() {
        threadLocal.set(TaskProgress.INITIAL_PROGRESS)
        repeat((1..max).count()) {
            try {
                sleep(500)
                threadLocal.set(it + 1)
                onProgress()
            } catch (e: InterruptedException) {
                return
            }
        }
    }

}