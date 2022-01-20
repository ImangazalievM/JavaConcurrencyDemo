import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.navigation.Routes
import ui.components.FlowRow
import ui.navigation.router.BaseWindow
import ui.navigation.Route
import windows.synchronizers.countdown.CountDownWindow
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
            Button(onClick = {}) {
                Text("Fork/Join Pool")
            }
            Button(onClick = {}) {
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
            Button(onClick = {}) {
                Text("Deadlock")
            }
            Button(onClick = {}) {
                Text("Livelock")
            }
            Button(onClick = {}) {
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
            Button(onClick = {}) {
                Text("Phaser")
            }
            Button(onClick = {
                router.push(Routes.CountDownLatch)
            }) {
                Text("CountDownLatch")
            }
            Button(onClick = {}) {
                Text("CyclicBarrier")
            }
            Button(onClick = {}) {
                Text("Semaphore")
            }
            Button(onClick = {}) {
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
            Button(onClick = {}) {
                Text("Lock")
            }
            Button(onClick = {}) {
                Text("ThreadLocal")
            }
            Button(onClick = {}) {
                Text("Mutex")
            }
        }
    }
}

