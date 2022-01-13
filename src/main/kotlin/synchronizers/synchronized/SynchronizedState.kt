package synchronizers.synchronized

import global.ui.mvp.State

data class SynchronizedState(
    val isSynchronized: Boolean,
    val threadCount: Int,
    val number: Int,
    val expectedNumber: Int,
    val isRunning: Boolean,
    val sum: Int
): State