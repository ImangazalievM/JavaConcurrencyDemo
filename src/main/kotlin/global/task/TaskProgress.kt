package global.task

data class TaskProgress(
    val taskNumber: Int,
    val max: Int,
    val progress: Int,
    val threadId: Int?,
    val finishedAt: Long
) {

    val isStarted: Boolean
        get() = progress != FooTask.INITIAL_PROGRESS
    val isFinished: Boolean
        get() = progress == max

}