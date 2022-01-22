package threads

open class TaskProgress(
    val taskNumber: Int,
    val max: Int,
    val progress: Int = INITIAL_PROGRESS
) {

    val status: TaskStatus
        get() = when {
            progress == max -> TaskStatus.FINISHED
            progress != INITIAL_PROGRESS -> TaskStatus.RUNNING
            else -> TaskStatus.PENDING
        }
    val isStarted: Boolean
        get() = progress != INITIAL_PROGRESS
    val isFinished: Boolean
        get() = progress == max

    companion object {
        const val INITIAL_PROGRESS = 0
    }
}