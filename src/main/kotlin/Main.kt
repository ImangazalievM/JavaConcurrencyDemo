import androidx.compose.desktop.DesktopTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import global.ui.navigation.router.BackPressHandler
import global.ui.navigation.router.LocalBackPressHandler
import global.ui.navigation.router.LocalRouting

fun main() = application {
    DesktopTheme {
        val backPressHandler = BackPressHandler()
        CompositionLocalProvider(
            LocalBackPressHandler provides backPressHandler,
            LocalRouting provides emptyList()
        ) {
            MainWindow().render()
        }
    }
}