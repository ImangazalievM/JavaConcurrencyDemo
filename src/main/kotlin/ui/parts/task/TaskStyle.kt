package ui.parts.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val shape = RoundedCornerShape(10.dp)

object TaskStyle {
    val RED = Modifier
        .background(Color(0xfff4cccc), shape)
        .border(2.dp, Color(0xff19456f), shape)
    val BLUE = Modifier
        .background(Color(0xffcfe2f3), shape)
        .border(2.dp, Color(0xff19456f), shape)
    val GREEN = Modifier
        .background(Color(0xffc9ead1), shape)
        .border(BorderStroke(2.dp, Color(0xff87d496)), shape)
    val GRAY = Modifier
        .background(Color(0xffe9edf0), shape)
        .border(2.dp, Color(0xffb4bed6), shape)
}