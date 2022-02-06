package windows.other.threadlocal

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.State

data class ThreadLocalState(
    val threadCount: Int,
    val progress: List<TaskProgress>,
    val status: TaskStatus,
) : State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}