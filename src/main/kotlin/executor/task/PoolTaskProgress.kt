package executor.task

data class PoolTaskProgress(
    val taskNumber: Int,
    val max: Int,
    val progress: Int,
    val threadId: Int?,
    val finishedAt: Long
) {

    val isStarted: Boolean
        get() = progress != PoolTask.INITIAL_PROGRESS
    val isFinished: Boolean
        get() = progress == max

}