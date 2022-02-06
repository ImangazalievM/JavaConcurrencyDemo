package windows.synchronizers.reentrantlock

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.State

data class ReentrantLockState(
    val threadCount: Int,
    val progress: List<TaskProgress>,
    val status: TaskStatus,
    val isLocked: Boolean,
): State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}