package windows.synchronizers.countdown

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.State

data class CountDownState(
    val threadCount: Int,
    val progress: List<TaskProgress>,
    val status: TaskStatus,
) : State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}