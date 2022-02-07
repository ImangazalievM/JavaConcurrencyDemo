package windows.synchronizers.exchanger

import threads.TaskStatus
import ui.mvp.Presenter
import java.util.concurrent.Exchanger

class ExchangerPresenter : Presenter<ExchangerState>() {

    private var semaphore = Exchanger<String?>()
    private var task1 = ExchangerTask(semaphore) {

    }
    private var task2 = ExchangerTask(semaphore) {

    }

    override fun getInitialState(): ExchangerState {
        return ExchangerState(
            message1 = null,
            message2 = null,
            status = TaskStatus.PENDING
        )
    }

    fun sendMessageTo1(count: Int) {

    }

    fun sendMessageTo2(count: Int) {

    }

    fun start() {
        updateState(state.copy(
            status = TaskStatus.RUNNING
        ))
        task1.start()
        task2.start()
    }
}