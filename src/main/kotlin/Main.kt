import androidx.compose.desktop.DesktopTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import ui.navigation.router.BackPressHandler
import ui.navigation.router.LocalBackPressHandler
import ui.navigation.router.LocalRouting

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