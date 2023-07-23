package di.modules

import dagger.Module
import dagger.Provides
import data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}