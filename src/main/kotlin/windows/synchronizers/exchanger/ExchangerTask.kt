package windows.synchronizers.exchanger

import java.util.concurrent.Exchanger

class ExchangerTask(
    private val exchanger: Exchanger<String?>,
    private val onMessageChanged: (String) -> Unit
) : Thread() {

    private var isActive: Boolean = false
    private var messageForSending: String? = null

    fun sendMessage(message: String) {
        this.messageForSending = message
    }

    override fun run() {
        while (isActive) {
            val receivedMessage = exchanger.exchange(messageForSending)
            if (receivedMessage != null) {
                onMessageChanged(receivedMessage)
                messageForSending = null
            }
        }
    }
}