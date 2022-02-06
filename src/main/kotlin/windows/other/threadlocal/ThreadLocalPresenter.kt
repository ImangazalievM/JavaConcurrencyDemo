package windows.other.threadlocal

import threads.TaskProgress
import threads.TaskStatus
import threads.TaskWaitingThread
import ui.mvp.Presenter
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

class ThreadLocalPresenter : Presenter<ThreadLocalState>() {

    private val threadLocal = ThreadLocal<Int>()
    private lateinit var tasks: List<ThreadLocalTask>

    override fun getInitialState(): ThreadLocalState {
        tasks = generateTasks(TASK_COUNT_DEFAULT)
        return ThreadLocalState(
            status = TaskStatus.PENDING,
            threadCount = TASK_COUNT_DEFAULT,
            progress = generateEmptyProgress()
        )
    }

    fun onThreadCountChanged(count: Int) {
        tasks = generateTasks(count)
        updateState(
            state.copy(
                threadCount = count,
                progress = generateEmptyProgress()
            )
        )
    }

    fun startCalculation() {
        updateState(
            state.copy(status = TaskStatus.RUNNING)
        )

        tasks.forEach {
            it.start()
        }
    }

    private fun generateTasks(size: Int): MutableList<ThreadLocalTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 15)
            ThreadLocalTask(threadLocal, taskNumber, duration) {
                updateProgress(taskNumber, TaskProgress(
                    taskNumber = taskNumber,
                    max = duration,
                    progress = threadLocal.get()
                ))
            }
        }.toMutableList()
    }

    private fun generateEmptyProgress() : List<TaskProgress> = tasks.map {
        TaskProgress(
            taskNumber = it.taskNumber,
            max = it.max,
            progress = TaskProgress.INITIAL_PROGRESS
        )
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
        updateState(
            state.copy(
                progress = newProgressList,
                status = status
            )
        )
    }

    companion object {
        const val TASK_COUNT_DEFAULT = 3
    }
}