package di.modules

import dagger.Module
import dagger.Provides
import data.MainRepository
import data.TmdbApi
import domain.Interactor
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) =
        Interactor(repo = repository, retrofitService = tmdbApi)
}