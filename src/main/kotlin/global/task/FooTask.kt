package global.task

class FooTask(
    private val taskNumber: Int,
    private val max: Int,
    private val onProgress: (TaskProgress) -> Unit
) : Runnable {

    private var threadId: Int? = null
    private var progressNumber: Int = INITIAL_PROGRESS
    private var finishedAt: Long = INITIAL_FINISHED_AT
    val progress: TaskProgress
        get() = createProgress(progressNumber)

    override fun run() {
        onProgress(createProgress(0))
        (1..max).forEach { progress ->
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                return
            }
            if (progress == max) {
                threadId = null
                finishedAt = System.currentTimeMillis()
            } else {
                threadId = (Thread.currentThread() as DemoThread).id
            }

            onProgress(createProgress(progress))
        }
    }

    private fun createProgress(progress: Int) = TaskProgress(
        taskNumber = taskNumber,
        max = max,
        progress = progress,
        threadId = threadId,
        finishedAt = finishedAt
    )

    companion object {
        const val INITIAL_PROGRESS = 0
        const val INITIAL_FINISHED_AT = 0L
    }
}