package windows.executor.task

import threads.TaskProgress

class PoolTaskProgress(
    taskNumber: Int,
    max: Int,
    progress: Int = INITIAL_PROGRESS,
    val threadId: Int?,
    val finishedAt: Long
) : TaskProgress(taskNumber, max, progress)