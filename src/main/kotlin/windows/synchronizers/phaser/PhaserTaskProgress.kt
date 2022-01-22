package windows.synchronizers.phaser

data class PhaserTaskProgress(
    val phases: List<Phase>,
    val taskNumber: Int,
    val currentPhaseIndex: Int = INITIAL_CURRENT_PHASE,
    val currentPhaseProgress: Int = INITIAL_PROGRESS
) {

    companion object {
        const val INITIAL_CURRENT_PHASE = 0
        const val INITIAL_PROGRESS = 0
    }
}