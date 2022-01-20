package windows.synchronizers.synchronized

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.SimpleOutlinedExposedDropDownMenu
import extensions.asStrings
import extensions.checkboxChangeListener
import ui.components.WindowContent
import ui.components.WindowHeader
import ui.mvp.BaseMvpWindow

class SynchronizedWindow : BaseMvpWindow<SynchronizedPresenter, SynchronizedState>() {

    override fun createPresenter(): SynchronizedPresenter {
        return SynchronizedPresenter()
    }

    @Composable
    override fun renderContent() = WindowContent {
        WindowHeader(title = "Synchronized") {
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
        Options()
        Spacer(modifier = Modifier.height(20.dp))
        Numbers()
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            enabled = !state().isRunning,
            onClick = { presenter.startCalculation() }
        ) {
            Text("Start")
        }
    }

    @Composable
    private fun Numbers() {
        val textSize = 20.sp
        Row {
            Text(
                "Expected - ",
                fontSize = textSize
            )
            Text(
                state.expectedNumber.toString(),
                fontSize = textSize
            )
        }
        Row {
            Text(
                "Actual - ",
                fontSize = textSize
            )
            Text(
                state.sum.toString(),
                color = when {
                    state.sum == state.expectedNumber -> Color(0xff008000)
                    state.sum == 0 || state.isRunning -> Color.Black
                    else -> Color(0xffB22222)
                },
                fontSize = textSize
            )
        }
    }

    @Composable
    fun Options() {
        val threadValues = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
        SimpleOutlinedExposedDropDownMenu(
            values = threadValues.asStrings(),
            selectedIndex = threadValues.indexOf(state().threadCount),
            label = {
                Text("Threads")
            },
            modifier = Modifier.requiredWidth(150.dp),
            onChange = { presenter.onThreadCountChanged(threadValues[it]) },
            backgroundColor = Color.White,
            enabled = !state().isRunning
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text("Synchronized" )
            Spacer(modifier = Modifier.width(5.dp))
            Switch(checked = state().isSynchronized, onCheckedChange = checkboxChangeListener {
                presenter.onSynchronizationEnabled(!state.isSynchronized)
            })
        }
    }
}