package executor

import executor.task.PoolTaskProgress
import global.ui.mvp.State

data class ThreadPoolExecutorState(
    val taskCount: Int,
    val threadCount: Int,
    val areThreadsRunning: Boolean,
    val progress: List<PoolTaskProgress>
): State