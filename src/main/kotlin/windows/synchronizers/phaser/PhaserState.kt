package windows.synchronizers.phaser

import threads.TaskStatus
import ui.mvp.State

data class PhaserState(
    val phaseCount: Int,
    val taskCount: Int,
    val tasks: List<PhaserTaskProgress>,
    val status: TaskStatus,
) : State {

    val isRunning: Boolean
        get() = status == TaskStatus.RUNNING

}