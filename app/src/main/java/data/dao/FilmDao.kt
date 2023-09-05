package data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.entily.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): Flow<List<Film>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

}