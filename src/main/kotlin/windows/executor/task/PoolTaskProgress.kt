package windows.executor.task

import threads.TaskProgress

class PoolTaskProgress(
    taskNumber: Int,
    max: Int,
    progress: Int,
    val threadId: Int?,
    val finishedAt: Long
) : TaskProgress(taskNumber, max, progress)