package windows.executor.task

class PoolThread(
    val id: Int,
    runnable: Runnable
) : Thread(runnable, "DemoThread: $id")