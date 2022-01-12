package executor

import global.task.DemoThread
import global.task.FooTask
import global.task.TaskProgress
import global.ui.mvp.Presenter
import java.util.concurrent.*
import kotlin.random.Random


class ThreadPoolExecutorPresenter : Presenter<ThreadPoolExecutorState>() {

    private lateinit var tasks: List<FooTask>
    private var executor: ExecutorService = createExecutorService(THREAD_COUNT_DEFAULT)

    override fun getInitialState(): ThreadPoolExecutorState {
        tasks = generateTasks(TASK_COUNT_DEFAULT)
        return ThreadPoolExecutorState(
            taskCount = TASK_COUNT_DEFAULT,
            threadCount = THREAD_COUNT_DEFAULT,
            areThreadsRunning = false,
            progress = tasks.map { it.progress }
        )
    }

    fun onTaskCountSelected(count: Int) {
        tasks = generateTasks(count)
        updateState(
            state.copy(
                taskCount = count,
                progress = tasks.map { it.progress }
            )
        )
    }

    fun onThreadCountSelected(count: Int) {
        executor = createExecutorService(count)
        updateState(state.copy(threadCount = count))
    }

    fun onStartTasksClick() {
        updateState(state.copy(areThreadsRunning = true))
        tasks.forEach { task ->
            executor.submit(task)
        }
    }

    fun onStopTasksClick() {
        executor.shutdownNow()
        updateState(state.copy(areThreadsRunning = false))

        onTaskCountSelected(state.taskCount)
        onThreadCountSelected(state.threadCount)
    }

    private fun createExecutorService(threadCount: Int) =
        Executors.newFixedThreadPool(threadCount, object : ThreadFactory {
            private var counter = 1
            override fun newThread(runnable: Runnable): Thread {
                return DemoThread(counter++, runnable)
            }
        })

    private fun generateTasks(size: Int): MutableList<FooTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 10)
            FooTask(taskNumber, duration) {
                updateProgress(taskNumber, it)
            }
        }.toMutableList()
    }

    @Synchronized
    private fun updateProgress(taskNumber: Int, progress: TaskProgress) {
        val newProgress = state.progress.toMutableList()
        newProgress[taskNumber - 1] = progress

        val isAllFinished = newProgress.all { it.isFinished }
        updateState(
            state.copy(
                progress = newProgress,
                areThreadsRunning = !isAllFinished
            )
        )
    }

    companion object {
        const val TASK_COUNT_DEFAULT = 5
        const val THREAD_COUNT_DEFAULT = 5
    }
}