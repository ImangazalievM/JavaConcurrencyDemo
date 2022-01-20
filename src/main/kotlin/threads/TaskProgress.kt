package threads

open class TaskProgress(
    val taskNumber: Int,
    val max: Int,
    val progress: Int
) : Thread() {

    val isStarted: Boolean
        get() = progress != INITIAL_PROGRESS
    val isFinished: Boolean
        get() = progress == max

    companion object {
        const val INITIAL_PROGRESS = 0
    }
}