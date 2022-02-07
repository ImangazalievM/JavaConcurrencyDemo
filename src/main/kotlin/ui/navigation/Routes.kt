package ui.navigation

sealed class Routes : Route() {
    object ThreadPoolExecutor : Routes()
    object Synchronized : Routes()
    object CountDownLatch : Routes()
    object Phaser : Routes()
    object ReentrantLock : Routes()
    object Semaphore : Routes()
    object Exchanger : Routes()
    object ThreadLocal : Routes()
}