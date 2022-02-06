package windows.executor

import ui.mvp.Presenter
import windows.executor.task.PoolTask
import windows.executor.task.PoolTaskProgress
import windows.executor.task.PoolThread
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.random.Random


class ThreadPoolExecutorPresenter : Presenter<ThreadPoolExecutorState>() {

    private lateinit var tasks: List<PoolTask>
    private var executor: ExecutorService = createExecutorService(POOL_SIZE_DEFAULT)

    override fun getInitialState(): ThreadPoolExecutorState {
        tasks = generateTasks(THREAD_COUNT_DEFAULT)
        return ThreadPoolExecutorState(
            threadCount = THREAD_COUNT_DEFAULT,
            poolSize = POOL_SIZE_DEFAULT,
            areThreadsRunning = false,
            progress = tasks.map { it.progress }
        )
    }

    fun onThreadCountSelected(count: Int) {
        tasks = generateTasks(count)
        updateState(
            state.copy(
                threadCount = count,
                progress = tasks.map { it.progress }
            )
        )
    }

    fun onPoolSizeSelected(count: Int) {
        executor = createExecutorService(count)
        updateState(state.copy(poolSize = count))
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

        onThreadCountSelected(state.threadCount)
        onPoolSizeSelected(state.poolSize)
    }

    private fun createExecutorService(threadCount: Int) =
        Executors.newFixedThreadPool(threadCount, object : ThreadFactory {
            private var counter = 1
            override fun newThread(runnable: Runnable): Thread {
                return PoolThread(counter++, runnable)
            }
        })

    private fun generateTasks(size: Int): MutableList<PoolTask> {
        return (1..size).map { taskNumber ->
            val duration = Random.nextInt(5, 10)
            PoolTask(taskNumber, duration) {
                updateProgress(taskNumber, it)
            }
        }.toMutableList()
    }

    @Synchronized
    private fun updateProgress(taskNumber: Int, progress: PoolTaskProgress) {
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
        const val THREAD_COUNT_DEFAULT = 5
        const val POOL_SIZE_DEFAULT = 5
    }
}