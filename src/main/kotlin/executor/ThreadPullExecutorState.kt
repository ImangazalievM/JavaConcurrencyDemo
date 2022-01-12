package executor

import global.task.TaskProgress
import global.ui.mvp.State

data class ThreadPullExecutorState(
    val taskCount: Int,
    val threadCount: Int,
    val areThreadsRunning: Boolean,
    val progress: List<TaskProgress>
): State