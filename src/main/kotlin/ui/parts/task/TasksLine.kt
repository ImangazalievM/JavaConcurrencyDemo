package ui.parts.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.FlowRow
import ui.modifiers.dashedBorder

@Composable
fun TasksLine(composable: @Composable () -> Unit) = Column {
    val tasksLineModifier = Modifier.fillMaxSize()
        .dashedBorder(2.dp, Color(0xffE0E0E0), RoundedCornerShape(10.dp), 5.dp, 2.dp)
        .padding(10.dp)
        .defaultMinSize(minHeight = 50.dp)

    FlowRow(
        modifier = tasksLineModifier,
        crossAxisSpacing = 10.dp,
        mainAxisSpacing = 10.dp,
        maxLineChild = 5
    ) {
        composable()
    }
}