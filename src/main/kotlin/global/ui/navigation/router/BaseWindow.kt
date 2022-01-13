package global.ui.navigation.router

import androidx.compose.desktop.AppManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import global.ui.navigation.DefaultRootRoute
import global.ui.navigation.Route
import global.ui.navigation.WindowOptions

abstract class BaseWindow {

    protected open var title: String by mutableStateOf("Java Threads Demo")
    private var rootRoute: Route = DefaultRootRoute()
    protected lateinit var router: BackStack<Route>
        private set

    @Composable
    open fun render() {
        Router(rootRoute) { backStack ->
            if (!this::router.isInitialized) this.router = backStack
            val currentRoute = backStack.last()
            if (rootRoute.separateWindow) {
                showInSeparateWindow(rootRoute.windowOptions) {
                    if (currentRoute == rootRoute) {
                        renderContent()
                    } else {
                        onNavigation(currentRoute)
                    }
                }
            } else {
                renderContent()
            }
        }
    }

    @Composable
    private fun showInSeparateWindow(
        options: WindowOptions,
        content: @Composable () -> Unit
    ) {
        Window(
            state = rememberWindowState(position = WindowPosition(options.alignment)),
            onCloseRequest = ::onCloseWindow,
            resizable = options.resizable,
            title = title
        ) {
            content()
        }
    }

    private fun onCloseWindow() = AppManager.windows.lastOrNull()?.close()

    @Composable
    protected abstract fun renderContent()

    @Composable
    protected open fun onNavigation(route: Route) {
        val window = getRouteWindow(route)
        if (!route.separateWindow) window.router = router
        window.rootRoute = route
        window.render()
    }

    protected open fun getRouteWindow(route: Route): BaseWindow {
        throw IllegalArgumentException("Unknown screen: ${route.javaClass.simpleName}")
    }
}