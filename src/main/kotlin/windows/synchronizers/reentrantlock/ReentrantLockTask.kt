package windows.synchronizers.reentrantlock

import threads.TaskProgress
import java.util.concurrent.locks.ReentrantLock

class ReentrantLockTask(
    private val reentrantLock: ReentrantLock,
    private val taskNumber: Int,
    private val max: Int,
    private val onProgress: (TaskProgress) -> Unit
) : Thread() {

    private var progressNumber: Int = TaskProgress.INITIAL_PROGRESS
    val progress: TaskProgress
        get() = createProgress()

    override fun run() {
        reentrantLock.lock()
        repeat((1..max).count()) {
            try {
                sleep(500)
                progressNumber = it + 1
                onProgress(progress)
            } catch (e: InterruptedException) {
                return
            }
        }
        reentrantLock.unlock()
        onProgress(progress)
    }

    private fun createProgress() = TaskProgress(
        taskNumber = taskNumber,
        max = max,
        progress = progressNumber
    )
}