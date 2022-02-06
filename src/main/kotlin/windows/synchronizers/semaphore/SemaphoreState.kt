package windows.synchronizers.semaphore

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.State

data class SemaphoreState(
    val threadCount: Int,
    val totalPermits: Int,
    val availablePermits: Int,
    val progress: List<TaskProgress>,
    val status: TaskStatus
): State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}