package ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrollBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scrollState = rememberLazyListState()
    LazyColumn(Modifier.fillMaxHeight().then(modifier), scrollState) {
        items(1) {
            content()
        }
    }
}
