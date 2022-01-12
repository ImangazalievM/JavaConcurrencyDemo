import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import global.ui.navigation.Routes
import global.ui.components.FlowRow
import global.ui.navigation.router.BaseWindow
import global.ui.navigation.Route

class MainWindow : BaseWindow() {

    override fun getContent(): @Composable () -> Unit = {
        Content()
    }

    override fun getRouteWindow(route: Route): BaseWindow {
        return when (route) {
            is Routes.ThreadPoolExecutor -> ThreadPoolExecutorWindow()
            else -> super.getRouteWindow(route)
        }
    }

    @Composable
    private fun Content() = Column(
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
            Button(onClick = {}) {
                Text("Phaser")
            }
            Button(onClick = {}) {
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

