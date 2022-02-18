import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.FlowRow
import ui.navigation.Route
import ui.navigation.Routes
import ui.navigation.router.BaseWindow
import windows.other.threadlocal.ThreadLocalWindow
import windows.synchronizers.countdown.CountDownWindow
import windows.synchronizers.exchanger.ExchangerWindow
import windows.synchronizers.phaser.PhaserWindow
import windows.synchronizers.synchronized.SynchronizedWindow

class MainWindow : BaseWindow() {

    @Composable
    override fun renderContent() = Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MultipleThreads()
        Spacer(modifier = Modifier.height(15.dp))
        Liveness()
        Spacer(modifier = Modifier.height(15.dp))
        Synchronizers()
        Spacer(modifier = Modifier.height(15.dp))
        Other()
    }

    override fun getRouteWindow(route: Route): BaseWindow {
        return when (route) {
            is Routes.ThreadPoolExecutor -> ThreadPoolExecutorWindow()
            is Routes.Synchronized -> SynchronizedWindow()
            is Routes.CountDownLatch -> CountDownWindow()
            is Routes.Phaser -> PhaserWindow()
            is Routes.ThreadLocal -> ThreadLocalWindow()
            is Routes.ReentrantLock -> ReentrantLockWindow()
            is Routes.Semaphore -> SemaphoreWindow()
            is Routes.Exchanger -> ExchangerWindow()
            else -> super.getRouteWindow(route)
        }
    }

    @Composable
    private fun MultipleThreads() {
        Text(
            text = "Multiple threads",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            alignment = Alignment.CenterHorizontally,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 3
        ) {
            Button(onClick = {
                router.push(Routes.ThreadPoolExecutor)
            }) {
                Text("Executors")
            }
            Button(enabled = false, onClick = {}) {
                Text("Fork/Join Pool")
            }
            Button(enabled = false, onClick = {}) {
                Text("Collections")
            }
        }
    }

    @Composable
    private fun Liveness() {
        Text(
            text = "Liveness",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            alignment = Alignment.CenterHorizontally,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 3
        ) {
            Button(enabled = false, onClick = {}) {
                Text("Deadlock")
            }
            Button(enabled = false, onClick = {}) {
                Text("Livelock")
            }
            Button(enabled = false, onClick = {}) {
                Text("Starvation")
            }
        }
    }

    @Composable
    private fun Synchronizers() {
        Text(
            text = "Synchronizers",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            alignment = Alignment.CenterHorizontally,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 3
        ) {
            Button(onClick = {
                router.push(Routes.Synchronized)
            }) {
                Text("Synchronized")
            }
            Button(onClick = {
                router.push(Routes.Phaser)
            }) {
                Text("Phaser")
            }
            Button(onClick = {
                router.push(Routes.CountDownLatch)
            }) {
                Text("CountDownLatch")
            }
            Button(enabled = false, onClick = {}) {
                Text("CyclicBarrier")
            }
            Button(onClick = {
                router.push(Routes.ReentrantLock)
            }) {
                Text("ReentrantLock")
            }
            Button(onClick = {
                router.push(Routes.Semaphore)
            }) {
                Text("Semaphore")
            }
            Button(onClick = {
                router.push(Routes.Exchanger)
            }) {
                Text("Exchanger<V>")
            }
        }
    }

    @Composable
    private fun Other() {
        Text(
            text = "Other",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            alignment = Alignment.CenterHorizontally,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 3
        ) {
            Button(onClick = {
                router.push(Routes.ThreadLocal)
            }) {
                Text("ThreadLocal")
            }
        }
    }
}

