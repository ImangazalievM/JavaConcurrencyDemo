package extensions

import androidx.compose.runtime.*

@Composable
fun clickListener(body: @Composable () -> Unit): () -> Unit  {
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
        body()
    }

    return { clicked = true }
}

@Composable
fun checkboxChangeListener(body: @Composable (Boolean) -> Unit): (Boolean) -> Unit  {
    var value by remember { mutableStateOf(false) }
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
        body(value)
    }

    return {
        value = it
        clicked = true
    }
}