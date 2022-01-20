package windows.synchronizers.countdown

import threads.TaskProgress
import threads.TaskStatus
import threads.TaskWaitingThread
import ui.mvp.Presenter
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

class CountDownPresenter : Presenter<CountDownState>() {

    private var countDownLatch: CountDownLatch = CountDownLatch(TASK_COUNT_DEFAULT)
    private lateinit var tasks: List<CountDownTask>

    override fun getInitialState(): CountDownState {
        tasks = generateTasks(TASK_COUNT_DEFAULT)
        return CountDownState(
            status = TaskStatus.PENDING,
            threadCount = TASK_COUNT_DEFAULT,
            progress = tasks.map { it.progress }
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

    fun startCalculation() {
        updateState(
            state.copy(status = TaskStatus.RUNNING)
        )
        TaskWaitingThread {
            countDownLatch.await()
            updateState(state.copy(status = TaskStatus.FINISHED))
            countDownLatch = CountDownLatch(tasks.size)
            tasks = generateTasks(state.threadCount)
        }.start()

        tasks.forEach {
            it.start()
        }
    }

    private fun generateTasks(size: Int): MutableList<CountDownTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 15)
            CountDownTask(countDownLatch, taskNumber, duration) {
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