package data

import android.content.ContentValues
import android.database.Cursor
import domain.Film

class MainRepository(dbHelper: DBHelper) {
    
    private val sqlDB = dbHelper.readableDatabase
    private var cursor: Cursor? = null
    
    fun putToDB(film: Film) {
        val contVal = ContentValues()
        contVal.apply {
            put(DBHelper.COLUMN_TITLE, film.title)
            put(DBHelper.COLUMN_POSTER, film.poster)
            put(DBHelper.COLUMN_DESC, film.description)
            put(DBHelper.COLUMN_RATING, film.rating)
        }
        
        sqlDB.insert(DBHelper.TABLE_NAME, null, contVal)
    }
    
    fun getAllFromDB(): List<Film> {
        cursor = sqlDB.rawQuery("SELECT * FROM ${DBHelper.TABLE_NAME}", null)
        val result = mutableListOf<Film>()
        if (cursor?.moveToFirst()!!) {
            do {
                val title = cursor?.getString(1)
                val poster = cursor?.getString(2)
                val description = cursor?.getString(3)
                val rating = cursor?.getDouble(4)
                
                result.add(Film(title!!, poster!!, description!!, rating!!, isInFavorites = true))
            } while (cursor?.moveToNext()!!)
            
        }
        cursor?.close()
        return result
    }
}