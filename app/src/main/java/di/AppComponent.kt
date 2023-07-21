package di

import dagger.Component
import di.modules.DatabaseModule
import di.modules.DomainModule
import di.modules.RemoteModule
import viewmodel.FavoriteFragmentViewModel
import viewmodel.HomeFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DomainModule::class,
        DatabaseModule::class
    ]
)

interface AppComponent {
    fun inject(homeFragVM: HomeFragmentViewModel)

    //заглушка
    fun inject(favorFragVM: FavoriteFragmentViewModel)
}