package synchronizers.synchronized

import executor.task.PoolTask
import global.threads.TaskWaitingThread
import global.ui.mvp.Presenter
import java.util.concurrent.*


class SynchronizedPresenter : Presenter<SynchronizedState>() {

    private lateinit var countDownLatch: CountDownLatch
    private lateinit var tasks: List<PoolTask>

    override fun getInitialState(): SynchronizedState {
        return SynchronizedState(
            isRunning = false,
            threadCount = 3,
            isSynchronized = true,
            expectedNumber = 300,
            number = 100,
            sum = 0
        )
    }

    fun onSynchronizationEnabled(isSynchronized: Boolean) {
        updateState(state.copy(isSynchronized = isSynchronized))
    }

    fun onThreadCountChanged(count: Int) {
        updateState(state.copy(
            threadCount = count,
            expectedNumber = count * state.number
        ))
    }

    fun startCalculation() {
        countDownLatch = CountDownLatch(state.threadCount)
        updateState(state.copy(
            sum = 0,
            isRunning = true
        ))
        repeat((1..state.threadCount).count()) {
            startThread()
        }
        TaskWaitingThread {
            countDownLatch.await()
            updateState(state.copy(isRunning = false))
        }.start()
    }

    private fun startThread() {
        if (state.isSynchronized) {
            SynchronizedTask(countDownLatch, state.number) {
                incrementProgressSync()
            }.start()
        } else {
            SynchronizedTask(countDownLatch, state.number) {
                incrementProgress()
            }.start()
        }
    }

    private fun incrementProgress() {
        updateState(state.copy(sum = state.sum + 1))
    }

    @Synchronized
    private fun incrementProgressSync() {
        updateState(state.copy(sum = state.sum + 1))
    }
}