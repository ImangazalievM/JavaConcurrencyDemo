package windows.synchronizers.exchanger

import threads.TaskStatus
import ui.mvp.State

data class ExchangerState(
    val message1: String?,
    val message2: String?,
    val status: TaskStatus
): State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}