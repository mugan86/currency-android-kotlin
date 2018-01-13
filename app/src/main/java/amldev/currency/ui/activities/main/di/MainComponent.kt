package amldev.kotlinandroidonlinecourse.ui.activities.main.di

/**
 * Created by anartzmugika on 7/1/18.
 */
import amldev.currency.ui.activities.main.MainActivity
import amldev.currency.ui.activities.main.di.MainModule
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)
}