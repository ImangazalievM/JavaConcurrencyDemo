import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import extensions.asStrings
import threads.TaskStatus
import ui.components.SimpleOutlinedExposedDropDownMenu
import ui.parts.WindowContent
import ui.parts.WindowHeader
import ui.modifiers.dashedBorder
import ui.mvp.BaseMvpWindow
import ui.parts.task.Task
import ui.parts.task.TasksLine
import ui.parts.task.style
import windows.executor.ThreadPoolExecutorPresenter
import windows.executor.ThreadPoolExecutorState

class ThreadPoolExecutorWindow : BaseMvpWindow<ThreadPoolExecutorPresenter, ThreadPoolExecutorState>() {

    override fun createPresenter(): ThreadPoolExecutorPresenter {
        return ThreadPoolExecutorPresenter()
    }

    @Composable
    override fun renderContent() = WindowContent {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            WindowHeader(title = "ThreadPoolExecutor") {
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
        ExecutorConfig(threadValues)
        Spacer(modifier = Modifier.height(15.dp))
        CommandButtons()
        Spacer(modifier = Modifier.height(10.dp))
        ThreadsVisualization()
    }

    @Composable
    private fun ExecutorConfig(threadValues: List<Int>) {
        Row {
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.threadCount),
                label = {
                    Text("Threads")
                },
                modifier = Modifier.requiredWidth(150.dp),
                onChange = { presenter.onThreadCountSelected(threadValues[it]) },
                backgroundColor = Color.White,
                enabled = !state.areThreadsRunning
            )
            Spacer(modifier = Modifier.width(10.dp))
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.poolSize),
                label = {
                    Text("Pool Size")
                },
                modifier = Modifier.requiredWidth(180.dp),
                onChange = { presenter.onPoolSizeSelected(threadValues[it]) },
                backgroundColor = Color.White,
                enabled = !state.areThreadsRunning
            )
        }
    }

    @Composable
    private fun CommandButtons() {
        Row {
            Button(
                enabled = !state.areThreadsRunning,
                onClick = presenter::onStartTasksClick
            ) {
                Text("Start")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                enabled = state.areThreadsRunning,
                onClick = presenter::onStopTasksClick
            ) {
                Text("Stop")
            }
        }
    }

    @Composable
    private fun ThreadsVisualization() = Column {
        Text("Pending", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        TasksLine {
            val pendingTasks = state.progress.filter { !it.isStarted }
            pendingTasks.forEach { task ->
                Task(
                    text = "Task ${task.taskNumber}",
                    style = TaskStatus.PENDING.style
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("In Progress", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        TasksLine {
            (1..state.poolSize).forEach { threadId ->
                val shape = RoundedCornerShape(10.dp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Thread $threadId")
                    Spacer(modifier = Modifier.height(10.dp))
                    val task = state.progress.firstOrNull { it.threadId == threadId }
                    val hasTask = task != null
                    val taskText = task?.let { "Task ${it.taskNumber}" } ?: "Empty"

                    Task(
                        text = taskText,
                        progress = task,
                        style = if (hasTask) {
                            TaskStatus.RUNNING.style
                        } else {
                            Modifier.background(Color(0xffF0F0F0), shape)
                                .dashedBorder(2.dp, Color(0xffA8A8A8), shape, 5.dp, 2.dp)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Finished", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        TasksLine {
            val finishedTasks = state.progress
                .filter { it.isFinished }
                .sortedBy { it.finishedAt }
            finishedTasks.forEach { task ->
                Task(
                    text = "Task ${task.taskNumber}",
                    style = TaskStatus.FINISHED.style
                )
            }
        }
    }
}