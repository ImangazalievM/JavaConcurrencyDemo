package windows.executor

import windows.executor.task.PoolTaskProgress
import ui.mvp.State

data class ThreadPoolExecutorState(
    val taskCount: Int,
    val threadCount: Int,
    val areThreadsRunning: Boolean,
    val progress: List<PoolTaskProgress>
): State