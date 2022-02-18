package windows.synchronizers.exchanger

import threads.TaskStatus
import ui.mvp.Presenter
import java.util.concurrent.Exchanger

class ExchangerPresenter : Presenter<ExchangerState>() {

    private var semaphore = Exchanger<String?>()
    private lateinit var task1: ExchangerTask
    private lateinit var task2: ExchangerTask

    override fun getInitialState(): ExchangerState {
        return ExchangerState(
            message1 = "",
            message2 = "",
            status = TaskStatus.PENDING
        )
    }

    fun sendMessageTo1(message: String) {
        task1.sendMessage(message)
    }

    fun sendMessageTo2(message: String) {
        task2.sendMessage(message)
    }

    fun start() {
        task1 = ExchangerTask("Task 1", semaphore) {
            updateState(state.copy(message2 = it))
        }
        task2 = ExchangerTask("Task 2", semaphore) {
            updateState(state.copy(message1 = it))
        }
        task1.start()
        task2.start()
        updateState(state.copy(status = TaskStatus.RUNNING))
    }


    fun stop() {
        task1.stopListening()
        task2.stopListening()
        updateState(state.copy(status = TaskStatus.FINISHED))
    }
}