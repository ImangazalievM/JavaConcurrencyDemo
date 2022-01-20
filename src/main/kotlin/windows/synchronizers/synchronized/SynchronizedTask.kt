package windows.synchronizers.synchronized

import java.util.concurrent.CountDownLatch

class SynchronizedTask(
    private val countDownLatch: CountDownLatch,
    private val number: Int,
    private val increment: () -> Unit
) : Thread() {

    override fun run() {
        repeat((1..number).count()) {
            try {
                sleep(10)
                increment()
            } catch (e: InterruptedException) {
                return
            }
        }
        countDownLatch.countDown()
    }

}