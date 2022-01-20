package windows.synchronizers.countdown

import threads.TaskProgress
import java.util.concurrent.CountDownLatch

class CountDownTask(
    private val countDownLatch: CountDownLatch,
    private val taskNumber: Int,
    private val max: Int,
    private val increment: (TaskProgress) -> Unit
) : Thread() {

    private var progressNumber: Int = TaskProgress.INITIAL_PROGRESS
    val progress: TaskProgress
        get() = createProgress()

    override fun run() {
        repeat((1..max).count()) {
            try {
                sleep(500)
                progressNumber = it + 1
                increment(progress)
            } catch (e: InterruptedException) {
                return
            }
        }
        countDownLatch.countDown()
    }

    private fun createProgress() = TaskProgress(
        taskNumber = taskNumber,
        max = max,
        progress = progressNumber
    )
}