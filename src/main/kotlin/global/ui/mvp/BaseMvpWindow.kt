package global.ui.mvp

import androidx.compose.runtime.Composable
import global.ui.navigation.router.BaseWindow

abstract class BaseMvpWindow<P : Presenter<S>, S : State>: BaseWindow() {

    private var localPresenter: P? = null
    protected val presenter: P
        get() = localPresenter ?: error("No Presenter provided")

    @Composable
    protected fun state(): S = presenter.state()

    @Composable
    override fun render() {
        val presenter = createPresenter()
        this.localPresenter = presenter
        if (presenter != null) {
            presenter.init()
            providePresenter(presenter) {
                super.render()
            }
        } else super.render()
    }

    protected open fun createPresenter(): P? = null

}