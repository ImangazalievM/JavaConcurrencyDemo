package ui.parts.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
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
import threads.TaskProgress
import threads.TaskStatus

@Composable
fun Task(
    text: String,
    progress: TaskProgress? = null,
    style: Modifier
) {
    val shape = RoundedCornerShape(10.dp)
    val width = 100.dp
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var taskWidth by remember { mutableStateOf(0f) }
        Text(
            text,
            modifier = Modifier
                .defaultMinSize(minWidth = width)
                .align(Alignment.CenterHorizontally)
                .then(style)
                .padding(15.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    taskWidth = coordinates.size.toSize().width
                }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (progress != null) {
            val progressPercents = progress.progress.toFloat() / progress.max
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = progressPercents,
                    modifier = Modifier
                        .width(with(LocalDensity.current) { taskWidth.toDp() })
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "${progress.progress}/${progress.max}",
                    fontSize = 10.sp
                )
            }
        }
    }
}

val TaskStatus.style: Modifier
    get() {
        val shape = RoundedCornerShape(10.dp)
        return when(this) {
            TaskStatus.PENDING -> Modifier
                .background(Color(0xffe9edf0), shape)
                .border(2.dp, Color(0xffb4bed6), shape)
            TaskStatus.RUNNING ->  Modifier
                .background(Color(0xff87CEEB), shape)
                .border(2.dp, Color(0xff6495ED), shape)
            TaskStatus.FINISHED -> Modifier
                .background(Color(0xffc9ead1), shape)
                .border(BorderStroke(2.dp, Color(0xff87d496)), shape)
        }
    }