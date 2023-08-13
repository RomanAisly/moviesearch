package data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import data.dao.FilmDao
import data.entily.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}