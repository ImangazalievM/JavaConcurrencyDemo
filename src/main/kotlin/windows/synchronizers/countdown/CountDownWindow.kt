package windows.synchronizers.countdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ui.modifiers.dashedBorder
import ui.mvp.BaseMvpWindow
import ui.parts.WindowContent
import ui.parts.WindowHeader
import ui.parts.task.Task
import ui.parts.task.TasksLine
import ui.parts.task.style

class CountDownWindow : BaseMvpWindow<CountDownPresenter, CountDownState>() {

    override fun createPresenter(): CountDownPresenter {
        return CountDownPresenter()
    }

    @Composable
    override fun renderContent() = WindowContent {
        WindowHeader(title = "CountDownLatch") {
            router.pop()
        }
        Spacer(modifier = Modifier.height(5.dp))
        Content()
    }

    @Composable
    private fun Content() = Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val threadValues = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
        SimpleOutlinedExposedDropDownMenu(
            values = threadValues.asStrings(),
            selectedIndex = threadValues.indexOf(state().threadCount),
            label = { Text("Threads") },
            modifier = Modifier.requiredWidth(150.dp),
            onChange = { presenter.onThreadCountChanged(threadValues[it]) },
            backgroundColor = Color.White,
            enabled = !state().isRunning
        )

        Spacer(modifier = Modifier.height(20.dp))

        TasksStatus()
        Spacer(modifier = Modifier.height(20.dp))
        ThreadsVisualization()
        Spacer(modifier = Modifier.height(20.dp))
        CountDown()
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = !state().isRunning,
            onClick = { presenter.startCalculation() }
        ) {
            Text("Start")
        }
    }

    @Composable
    private fun CountDown() {
        val shape = RoundedCornerShape(10.dp)

        val finishedTasks = state.progress.filter { it.isFinished }
        val count = state.progress.size - finishedTasks.size
        Text(
            "CountDownLatch: $count",
            fontSize = 20.sp,
            modifier = Modifier.background(Color(0xffF0F0F0), shape)
                .dashedBorder(2.dp, Color(0xffA8A8A8), shape, 5.dp, 2.dp)
                .padding(10.dp)
        )
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
}