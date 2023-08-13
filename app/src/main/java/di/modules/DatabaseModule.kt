package di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import data.MainRepository
import data.dao.FilmDao
import data.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDBHelper(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "film_db").build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}