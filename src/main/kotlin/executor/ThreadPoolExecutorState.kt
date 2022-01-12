package executor

import global.task.TaskProgress
import global.ui.mvp.State

data class ThreadPoolExecutorState(
    val taskCount: Int,
    val threadCount: Int,
    val areThreadsRunning: Boolean,
    val progress: List<TaskProgress>
): State