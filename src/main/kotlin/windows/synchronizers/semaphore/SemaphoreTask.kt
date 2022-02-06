package windows.synchronizers.semaphore

import threads.TaskProgress
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock

class SemaphoreTask(
    private val semaphore: Semaphore,
    private val taskNumber: Int,
    private val max: Int,
    private val onProgress: (TaskProgress) -> Unit
) : Thread() {

    private var progressNumber: Int = TaskProgress.INITIAL_PROGRESS
    val progress: TaskProgress
        get() = createProgress()

    override fun run() {
        semaphore.acquire()
        repeat((1..max).count()) {
            try {
                sleep(500)
                progressNumber = it + 1
                onProgress(progress)
            } catch (e: InterruptedException) {
                return
            }
        }
        semaphore.release()
        onProgress(progress)
    }

    private fun createProgress() = TaskProgress(
        taskNumber = taskNumber,
        max = max,
        progress = progressNumber
    )
}