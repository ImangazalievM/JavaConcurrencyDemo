package windows.synchronizers.phaser

import java.util.concurrent.Phaser

class PhaserTask(
    private val phaser: Phaser,
    val taskNumber: Int,
    val phases: List<Phase>,
    private val onProgress: () -> Unit
) : Thread() {

    var progress = PhaserTaskProgress(phases, taskNumber)
        private set

    init {
        phaser.register()
    }

    override fun run() {
        repeat(phases.indices.count()) { phaseIndex ->
            val phase = phases[phaseIndex]
            progress = progress.copy(currentPhaseIndex = phaseIndex)
            (1..phase.max).forEach {
                try {
                    sleep(250)
                    progress = progress.copy(currentPhaseProgress = it)
                    onProgress()
                } catch (e: InterruptedException) {
                    phaser.arriveAndDeregister()
                    return
                }
            }
            if (phaseIndex == phases.size - 1) {
                phaser.arriveAndDeregister()
            } else {
                phaser.arriveAndAwaitAdvance()
            }
        }
    }
}