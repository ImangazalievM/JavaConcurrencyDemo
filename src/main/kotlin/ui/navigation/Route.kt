package ui.navigation

open class Route {
    var separateWindow: Boolean = false
    var windowOptions = WindowOptions()
}

class DefaultRootRoute: Route() {

    init {
        separateWindow = true
    }

}