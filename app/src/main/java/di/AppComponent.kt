package di

import dagger.Component
import di.modules.DatabaseModule
import di.modules.DomainModule
import di.modules.RemoteModule
import viewmodel.FavoriteFragmentViewModel
import viewmodel.HomeFragmentViewModel
import viewmodel.SettingsFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)

interface AppComponent {
    fun inject(homeFragVM: HomeFragmentViewModel)

    fun inject(favorFragVM: FavoriteFragmentViewModel)

    fun inject(settingsFragVM: SettingsFragmentViewModel)
}