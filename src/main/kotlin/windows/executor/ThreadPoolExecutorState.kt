package windows.executor

import ui.mvp.State
import windows.executor.task.PoolTaskProgress

data class ThreadPoolExecutorState(
    val threadCount: Int,
    val poolSize: Int,
    val areThreadsRunning: Boolean,
    val progress: List<PoolTaskProgress>
): State