package net.apocalypt.modernmovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.apocalypt.modernmovies.model.ListTypeConverters
import net.apocalypt.modernmovies.model.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(ListTypeConverters::class)
abstract class TMDBDatabase : RoomDatabase() {

    abstract fun discoverDao(): DiscoverDao

    companion object {

        @Volatile
        private var INSTANCE: TMDBDatabase? = null

        fun getInstance(context: Context): TMDBDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context) : TMDBDatabase {
            return Room.databaseBuilder(context.applicationContext, TMDBDatabase::class.java, "TMDB.db")
                    .build()
        }
    }

}