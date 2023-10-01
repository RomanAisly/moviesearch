package di

import com.example.remote_module.RemoteProvider
import dagger.Component
import di.modules.DatabaseModule
import di.modules.DomainModule
import viewmodel.FavoriteFragmentViewModel
import viewmodel.HomeFragmentViewModel
import viewmodel.SettingsFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(dependencies = [RemoteProvider::class],
    modules = [DatabaseModule::class, DomainModule::class])

interface AppComponent {
    fun inject(homeFragVM: HomeFragmentViewModel)

    fun inject(favorFragVM: FavoriteFragmentViewModel)

    fun inject(settingsFragVM: SettingsFragmentViewModel)
}