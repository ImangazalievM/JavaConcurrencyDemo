package windows.synchronizers.semaphore

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.Presenter
import java.util.concurrent.Semaphore
import kotlin.random.Random

class SemaphorePresenter : Presenter<SemaphoreState>() {

    private var semaphore = Semaphore(PERMIT_COUNT)
    private lateinit var tasks: List<SemaphoreTask>

    override fun getInitialState(): SemaphoreState {
        tasks = generateTasks(THREAD_COUNT_DEFAULT)
        return SemaphoreState(
            threadCount = THREAD_COUNT_DEFAULT,
            totalPermits = semaphore.availablePermits(),
            availablePermits = semaphore.availablePermits(),
            progress = tasks.map { it.progress },
            status = TaskStatus.PENDING
        )
    }

    fun onThreadCountChanged(count: Int) {
        tasks = generateTasks(count)
        updateState(
            state.copy(
                threadCount = count,
                progress = tasks.map { it.progress }
            )
        )
    }

    fun onPermitCountChanged(count: Int) {
        semaphore = Semaphore(count)
        updateState(
            state.copy(
                totalPermits = count,
                availablePermits = semaphore.availablePermits()
            )
        )
    }

    fun start() {
        updateState(state.copy(
            status = TaskStatus.RUNNING,
            progress = tasks.map { it.progress }
        ))

        tasks.forEach {
            it.start()
        }
    }

    private fun generateTasks(size: Int): MutableList<SemaphoreTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 15)
            SemaphoreTask(semaphore, taskNumber, duration) {
                updateProgress(taskNumber, it)
            }
        }.toMutableList()
    }

    @Synchronized
    private fun updateProgress(taskNumber: Int, progress: TaskProgress) {
        val newProgressList = state.progress.toMutableList()
        newProgressList[taskNumber - 1] = progress

        val isAllFinished = newProgressList.all { it.isFinished }
        val isAnyStarted = newProgressList.any { it.isStarted }

        val status = when {
            isAllFinished -> TaskStatus.FINISHED
            !isAnyStarted -> TaskStatus.PENDING
            else -> TaskStatus.RUNNING
        }

        if (status == TaskStatus.FINISHED) {
            semaphore = Semaphore(state.totalPermits)
            tasks = generateTasks(state.threadCount)
        }

        updateState(
            state.copy(
                progress = newProgressList,
                status = status,
                availablePermits = semaphore.availablePermits()
            )
        )
    }

    companion object {
        const val THREAD_COUNT_DEFAULT = 3
        const val PERMIT_COUNT = 3
    }
}