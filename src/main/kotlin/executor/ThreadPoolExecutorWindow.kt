import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import executor.SimpleOutlinedExposedDropDownMenu
import executor.ThreadPoolExecutorPresenter
import executor.ThreadPoolExecutorState
import global.extensions.asStrings
import global.ui.components.FlowRow
import global.ui.components.ScrollBar
import global.ui.components.WindowContent
import global.ui.components.WindowHeader
import global.ui.modifiers.dashedBorder
import global.ui.mvp.BaseMvpWindow

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
        //TasksProgress()
    }

    @Composable
    private fun ExecutorConfig(threadValues: List<Int>) {
        Row {
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.taskCount),
                label = {
                    Text("Tasks")
                },
                modifier = Modifier.requiredWidth(150.dp),
                onChange = { presenter.onTaskCountSelected(threadValues[it]) },
                backgroundColor = Color.White,
                enabled = !state.areThreadsRunning
            )
            Spacer(modifier = Modifier.width(10.dp))
            SimpleOutlinedExposedDropDownMenu(
                values = threadValues.asStrings(),
                selectedIndex = threadValues.indexOf(state.threadCount),
                label = {
                    Text("Threads")
                },
                modifier = Modifier.requiredWidth(180.dp),
                onChange = { presenter.onThreadCountSelected(threadValues[it]) },
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
        val tasksLineModifier = Modifier.fillMaxSize()
            .dashedBorder(2.dp, Color(0xffE0E0E0), RoundedCornerShape(10.dp), 5.dp, 2.dp)
            .padding(10.dp)
            .defaultMinSize(minHeight = 50.dp)
        Text("Pending", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = tasksLineModifier,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 5
        ) {
            val pendingTasks = state.progress.filter { !it.isStarted }
            pendingTasks.forEach { task ->
                val shape = RoundedCornerShape(10.dp)
                val width = 100.dp
                Column {
                    Text(
                        "Task ${task.taskNumber}",
                        modifier = Modifier
                            .defaultMinSize(minWidth = width)
                            .background(Color(0xffe9edf0), shape)
                            .border(2.dp, Color(0xffb4bed6), shape)
                            .padding(15.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("In Progress", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = tasksLineModifier,
            alignment = Alignment.CenterHorizontally,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 5
        ) {
            (1..state.threadCount).forEach { threadId ->
                val shape = RoundedCornerShape(10.dp)
                val width = 100.dp
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Thread $threadId"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    val task = state.progress.firstOrNull {
                        it.threadId == threadId
                    }
                    val hasTask = task != null
                    val taskText = task?.let {
                        "Task ${it.taskNumber}"
                    } ?: "Empty"

                    val taskBackground = if (hasTask) 0xff87CEEB else 0xffF0F0F0
                    val taskBorder = if (hasTask) 0xff6495ED else 0xffA8A8A8
                    var taskWidth by remember { mutableStateOf(0f) }
                    Text(
                        taskText,
                        modifier = Modifier
                            .defaultMinSize(minWidth = width)
                            .background(Color(taskBackground), shape)
                            .run {
                                if (hasTask) {
                                    border(2.dp, Color(taskBorder), shape)
                                } else {
                                    dashedBorder(2.dp, Color(taskBorder), shape, 5.dp, 2.dp)
                                }
                            }
                            .padding(15.dp)
                            .onGloballyPositioned { coordinates ->
                                //This value is used to assign to the DropDown the same width
                                taskWidth = coordinates.size.toSize().width
                            }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (task != null) {
                        val progressPercents = task.progress.toFloat() / task.max
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            LinearProgressIndicator(
                                progress = progressPercents,
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { taskWidth.toDp() })
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "${task.progress}/${task.max}",
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Finished", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = tasksLineModifier,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp,
            maxLineChild = 5
        ) {
            val finishedTasks = state.progress
                .filter { it.isFinished }
                .sortedBy { it.finishedAt }
            finishedTasks.forEach { task ->
                val shape = RoundedCornerShape(10.dp)
                val width = 100.dp
                Column {
                    Text(
                        "Task ${task.taskNumber}",
                        modifier = Modifier
                            .defaultMinSize(minWidth = width)
                            .background(Color(0xffc9ead1), shape)
                            .border(BorderStroke(2.dp, Color(0xff87d496)), shape)
                            .padding(15.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun TasksProgress() {
        Column {
            state.progress.forEachIndexed { index, progress ->
                Spacer(modifier = Modifier.height(5.dp))
                val progressPercents = progress.progress.toFloat() / progress.max
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${index + 1} - ", style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(progress.progress.toString())
                    Spacer(modifier = Modifier.width(5.dp))
                    RoundedLinearProgressIndicator(
                        modifier = Modifier.size(
                            width = 350.dp,
                            height = 10.dp,
                        ),
                        progress = progressPercents
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(progress.max.toString())
                }
            }
        }
    }
}