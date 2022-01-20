package ui.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun WindowHeader(
    title: String,
    onBackClicked: () -> Unit
) = Row(verticalAlignment = Alignment.CenterVertically) {
    Box(
        modifier = Modifier.padding(10.dp)
            .clickable { onBackClicked() }
    ) {
        Icon(
            painter = painterResource("ic_arrow_left.svg"),
            contentDescription = null,
            modifier = Modifier.width(20.dp)
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(title, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(10.dp))
    }
}