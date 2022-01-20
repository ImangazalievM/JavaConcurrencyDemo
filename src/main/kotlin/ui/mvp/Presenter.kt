package ui.mvp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

abstract class Presenter<S : State> {

    private var isInitialized: Boolean = false
    protected lateinit var state: S
    private lateinit var mutableState: MutableState<S>

    @Composable
    fun init() {
        if (!isInitialized) {
            val state = getInitialState()
            mutableState = remember { mutableStateOf(state) }
            updateState(state)
            isInitialized = true
        }
    }

    fun updateState(state: S) {
        this.state = state
        this.mutableState.value = state
    }

    @Composable
    fun state(): S = mutableState.value

    abstract fun getInitialState(): S

    open fun onInit() = Unit

}