package ui.navigation

sealed class Routes : Route() {
    object ThreadPoolExecutor : Routes()
    object Synchronized : Routes()
    object CountDownLatch : Routes()
}