package windows.synchronizers.phaser

import threads.TaskStatus
import threads.TaskWaitingThread
import ui.mvp.Presenter
import java.util.concurrent.Phaser
import kotlin.random.Random

class PhaserPresenter : Presenter<PhaserState>() {

    private var phaser: Phaser = Phaser()
    private lateinit var tasks: List<PhaserTask>

    override fun getInitialState(): PhaserState {
        generateTasks(
            taskCount = TASK_COUNT_DEFAULT,
            phaseCount = PHASE_COUNT_DEFAULT
        )
        return PhaserState(
            phaseCount = PHASE_COUNT_DEFAULT,
            taskCount = TASK_COUNT_DEFAULT,
            status = TaskStatus.PENDING,
            tasks = collectProgresses()
        )
    }

    fun onPhaseCountChanged(count: Int) {
        generateTasks(phaseCount = count)
        updateState(state.copy(
            phaseCount = count,
            tasks = collectProgresses()
        ))
    }

    fun onThreadCountChanged(count: Int) {
        generateTasks(taskCount = count)
        updateState(
            state.copy(
                taskCount = count,
                tasks = collectProgresses()
            )
        )
    }

    fun startCalculation() {
        updateState(state.copy(status = TaskStatus.RUNNING))
        TaskWaitingThread {
            val maxPhase = tasks.maxOf { it.phases.size }
            (0 until maxPhase).forEach {
                phaser.awaitAdvance(it)
            }
            updateState(state.copy(status = TaskStatus.FINISHED))
            generateTasks()
        }.start()

        tasks.forEach {
            it.start()
        }
    }

    fun stop() {
        phaser.forceTermination()
    }

    private fun generateTasks(
        taskCount: Int  = state.taskCount,
        phaseCount: Int = state.phaseCount
    ) {
        phaser = Phaser()
        tasks = (1..taskCount).map { taskNumber ->
            val phaseCount = Random.nextInt(1, phaseCount + 1)
            val phases = (1..phaseCount).map {
                Phase(it, Random.nextInt(5, 15))
            }
            PhaserTask(phaser, taskNumber, phases) {
                updateState()
            }
        }
    }

    @Synchronized
    private fun updateState() = updateState(state.copy(
        tasks = collectProgresses()
    ))

    private fun collectProgresses() = tasks.map { it.progress }

    companion object {
        const val PHASE_COUNT_DEFAULT = 4
        const val TASK_COUNT_DEFAULT = 3
    }
}