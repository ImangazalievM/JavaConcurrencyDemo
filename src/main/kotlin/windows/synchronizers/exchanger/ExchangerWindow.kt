package windows.synchronizers.exchanger

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extensions.asStrings
import threads.TaskStatus
import ui.components.SimpleOutlinedExposedDropDownMenu
import ui.mvp.BaseMvpWindow
import ui.parts.WindowContent
import ui.parts.WindowHeader
import ui.parts.task.Task
import ui.parts.task.TaskStyle
import ui.parts.task.TasksLine
import ui.parts.task.style
import windows.synchronizers.semaphore.SemaphorePresenter
import windows.synchronizers.semaphore.SemaphoreState

class ExchangerWindow : BaseMvpWindow<ExchangerPresenter, ExchangerState>() {

    override fun createPresenter(): ExchangerPresenter {
        return ExchangerPresenter()
    }

    @Composable
    override fun renderContent() = WindowContent {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            WindowHeader(title = "Semaphore") {
                router.pop()
            }
            Spacer(modifier = Modifier.height(5.dp))
            Content()
        }
    }

    @Composable
    private fun Content() = Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val threadValues = listOf(3, 4, 5, 6, 7, 8, 9, 10)
        Config(threadValues)
        Spacer(modifier = Modifier.height(10.dp))
        TasksStatus()
        Spacer(modifier = Modifier.height(20.dp))
        ThreadsVisualization()
        Spacer(modifier = Modifier.height(20.dp))
        RentrantLockStatus()
        Spacer(modifier = Modifier.height(20.dp))
        CommandButtons()
    }

    @Composable
    private fun Config(threadValues: List<Int>) {
        Row {
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.threadCount),
                label = {
                    Text("Threads")
                },
                modifier = Modifier.requiredWidth(150.dp),
                onChange = { presenter.onThreadCountChanged(threadValues[it]) },
                backgroundColor = Color.White,
                enabled = !state.isRunning
            )
            Spacer(modifier = Modifier.width(20.dp))
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.totalPermits),
                label = {
                    Text("Permits")
                },
                modifier = Modifier.requiredWidth(150.dp),
                onChange = { presenter.onPermitCountChanged(threadValues[it]) },
                backgroundColor = Color.White,
                enabled = !state.isRunning
            )
        }
    }

    @Composable
    private fun TasksStatus() {
        val textSize = 20.sp
        Row {
            Text(
                "Status: ",
                fontSize = textSize
            )
            Text(
                when (state.status) {
                    TaskStatus.RUNNING -> "Running"
                    TaskStatus.FINISHED -> "Finished"
                    else -> "Idle"
                },
                color = when (state.status) {
                    TaskStatus.RUNNING -> Color(0xffFF4500)
                    TaskStatus.FINISHED -> Color(0xff008000)
                    else -> Color.Black
                },
                fontSize = textSize
            )
        }
    }

    @Composable
    private fun ThreadsVisualization() = Column {
        TasksLine {
            state.progress.forEach { task ->
                Task(
                    "Task ${task.taskNumber}",
                    progress = task,
                    style = task.status.style
                )
            }
        }
    }

    @Composable
    private fun RentrantLockStatus() = Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Semaphore:")
        Spacer(modifier = Modifier.height(5.dp))
        val isFree = state.availablePermits == state.totalPermits
        val lockStyle = if (isFree) TaskStyle.GRAY else TaskStyle.BLUE
        Text(
            "Permits: ${state.availablePermits}/${state.totalPermits}",
            modifier = Modifier
                .then(lockStyle)
                .padding(10.dp)
        )
    }

    @Composable
    private fun CommandButtons() {
        Button(
            enabled = !state.isRunning,
            onClick = presenter::start
        ) {
            Text("Start")
        }
    }
}