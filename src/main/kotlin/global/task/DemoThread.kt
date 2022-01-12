package global.task

class DemoThread(
    val id: Int,
    runnable: Runnable
) : Thread(runnable, "DemoThread: $id")