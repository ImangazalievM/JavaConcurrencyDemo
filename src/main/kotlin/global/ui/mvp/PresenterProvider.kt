package global.ui.mvp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val PresenterProvider = compositionLocalOf<Presenter<*>> {
    error("No Presenter provided!")
}

@Composable
fun <T: Presenter<*>> providePresenter(presenter: T, content: @Composable () -> Unit) {
    CompositionLocalProvider(PresenterProvider provides presenter, content = content)
}