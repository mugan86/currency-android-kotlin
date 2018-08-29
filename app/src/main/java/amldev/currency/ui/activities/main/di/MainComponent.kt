package amldev.currency.ui.activities.main.di

/**
 * Created by anartzmugika on 7/1/18.
 */
import amldev.currency.ui.activities.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}