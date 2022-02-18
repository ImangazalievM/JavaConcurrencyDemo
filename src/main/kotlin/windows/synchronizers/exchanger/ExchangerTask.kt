package windows.synchronizers.exchanger

import java.util.concurrent.Exchanger

class ExchangerTask(
    id: String,
    private val exchanger: Exchanger<String?>,
    private val onMessageChanged: (String) -> Unit
) : Thread(id) {

    private var isActive: Boolean = true
    private var messageForSending: String? = null

    fun sendMessage(message: String) {
        this.messageForSending = message
    }

    fun stopListening() {
        isActive = false
    }

    override fun run() {
        while (isActive) {
            val receivedMessage = exchanger.exchange(messageForSending)
            if (receivedMessage != null) {
                onMessageChanged(receivedMessage)
            }
        }
    }
}