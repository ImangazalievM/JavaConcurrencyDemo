package windows.synchronizers.reentrantlock

import threads.TaskProgress
import threads.TaskStatus
import ui.mvp.Presenter
import java.util.concurrent.locks.ReentrantLock
import kotlin.random.Random

class ReentrantLockPresenter : Presenter<ReentrantLockState>() {

    private val reentrantLock = ReentrantLock()
    private lateinit var tasks: List<ReentrantLockTask>

    override fun getInitialState(): ReentrantLockState {
        tasks = generateTasks(THREAD_COUNT_DEFAULT)
        return ReentrantLockState(
            threadCount = THREAD_COUNT_DEFAULT,
            progress = tasks.map { it.progress },
            status = TaskStatus.PENDING,
            isLocked = false
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

    fun start() {
        updateState(state.copy(
            status = TaskStatus.RUNNING,
            progress = tasks.map { it.progress }
        ))

        tasks.forEach {
            it.start()
        }
    }

    private fun generateTasks(size: Int): MutableList<ReentrantLockTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 15)
            ReentrantLockTask(reentrantLock, taskNumber, duration) {
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
            tasks = generateTasks(state.threadCount)
        }

        updateState(
            state.copy(
                progress = newProgressList,
                status = status,
                isLocked = reentrantLock.isLocked
            )
        )
    }

    companion object {
        const val THREAD_COUNT_DEFAULT = 3
    }
}