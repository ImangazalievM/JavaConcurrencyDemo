package windows.synchronizers.exchanger

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.mvp.BaseMvpWindow
import ui.parts.WindowContent
import ui.parts.WindowHeader

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
            WindowHeader(title = "Exchanger") {
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
        Config()
        Spacer(modifier = Modifier.height(10.dp))
        ControlButtons()
    }

    @Composable
    private fun Config() {
        Row {
            var message1 by remember { mutableStateOf("") }
            Column {
                TextField(
                    value = message1,
                    onValueChange = { message1 = it },
                    label = { Text("Thread 1") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = state().isRunning,
                    onClick = { presenter.sendMessageTo2(message1) }
                ) {
                    Text("Send")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = state.message1)
            }
            Spacer(modifier = Modifier.width(20.dp))
            var message2 by remember { mutableStateOf("") }
            Column {
                TextField(
                    value = message2,
                    onValueChange = { message2 = it },
                    label = { Text("Thread 2") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = state().isRunning,
                    onClick = { presenter.sendMessageTo1(message2) }
                ) {
                    Text("Send")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = state.message2)
            }
        }
    }


    @Composable
    private fun ControlButtons() = Row {
        Button(
            enabled = !state().isRunning,
            onClick = { presenter.start() }
        ) {
            Text("Start")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            enabled = state().isRunning,
            onClick = { presenter.stop() }
        ) {
            Text("Stop")
        }
    }
}