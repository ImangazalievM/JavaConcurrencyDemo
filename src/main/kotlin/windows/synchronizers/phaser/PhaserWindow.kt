package windows.synchronizers.phaser

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
import threads.TaskProgress
import threads.TaskStatus
import ui.components.SimpleOutlinedExposedDropDownMenu
import ui.mvp.BaseMvpWindow
import ui.parts.WindowContent
import ui.parts.WindowHeader
import ui.parts.task.Task
import ui.parts.task.TaskStyle
import ui.parts.task.TasksLine

class PhaserWindow : BaseMvpWindow<PhaserPresenter, PhaserState>() {

    override fun createPresenter(): PhaserPresenter {
        return PhaserPresenter()
    }

    @Composable
    override fun renderContent() = WindowContent {
        WindowHeader(title = "Phaser") {
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
        Config()
        Spacer(modifier = Modifier.height(20.dp))
        TasksStatus()
        Spacer(modifier = Modifier.height(20.dp))
        ControlButtons()
        ThreadsVisualization()
    }

    @Composable
    private fun ControlButtons() = Row {
        Button(
            enabled = !state().isRunning,
            onClick = { presenter.startCalculation() }
        ) {
            Text("Start")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            enabled = !state().isRunning,
            onClick = { presenter.stop() }
        ) {
            Text("Stop")
        }
    }

    @Composable
    private fun Config() = Row {
        val phaseValues = listOf(2, 3, 4, 5, 6)
        SimpleOutlinedExposedDropDownMenu(
            values = phaseValues.asStrings(),
            selectedIndex = phaseValues.indexOf(state().phaseCount),
            label = { Text("Phases") },
            modifier = Modifier.requiredWidth(150.dp),
            onChange = { presenter.onPhaseCountChanged(phaseValues[it]) },
            backgroundColor = Color.White,
            enabled = !state().isRunning
        )

        Spacer(modifier = Modifier.width(10.dp))
        val threadValues = listOf(2, 3, 4)
        SimpleOutlinedExposedDropDownMenu(
            values = threadValues.asStrings(),
            selectedIndex = threadValues.indexOf(state().taskCount),
            label = { Text("Threads") },
            modifier = Modifier.requiredWidth(150.dp),
            onChange = { presenter.onThreadCountChanged(threadValues[it]) },
            backgroundColor = Color.White,
            enabled = !state().isRunning
        )
    }

    @Composable
    private fun TasksStatus() = Row {
        val textSize = 20.sp
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

    @Composable
    private fun ThreadsVisualization() = Column {
        state.tasks.forEach { task ->
            Spacer(modifier = Modifier.height(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Task ${task.taskNumber}")
                Spacer(modifier = Modifier.height(10.dp))
                TasksLine {
                    task.phases.forEachIndexed { phaseIndex, phase ->
                        val isCompeted = phaseIndex < task.currentPhaseIndex || task.currentPhaseIndex == phaseIndex
                                && task.currentPhaseProgress == phase.max
                        val isPending = phaseIndex > task.currentPhaseIndex
                        val style = when {
                            isCompeted -> TaskStyle.GREEN
                            isPending -> TaskStyle.GRAY
                            else -> TaskStyle.BLUE
                        }
                        val progress = when {
                            isCompeted -> TaskProgress(0, phase.max, phase.max)
                            isPending -> TaskProgress(0, phase.max, 0)
                            else -> TaskProgress(0, phase.max, task.currentPhaseProgress)
                        }
                        Task(
                            "Phase ${phaseIndex + 1}",
                            progress = progress,
                            style = style
                        )
                    }
                }
            }
        }
    }
}