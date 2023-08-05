package di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import data.DBHelper
import data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDBHelper(context: Context) = DBHelper(context)
    
    @Provides
    @Singleton
    fun provideRepository(dbHelper: DBHelper) = MainRepository(dbHelper)
}