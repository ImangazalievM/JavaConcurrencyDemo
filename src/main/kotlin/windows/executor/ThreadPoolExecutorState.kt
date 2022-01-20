package windows.executor

import ui.mvp.State
import windows.executor.task.PoolTaskProgress

data class ThreadPoolExecutorState(
    val taskCount: Int,
    val threadCount: Int,
    val areThreadsRunning: Boolean,
    val progress: List<PoolTaskProgress>
): State