package net.apocalypt.modernmovies.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.apocalypt.modernmovies.model.Result

@Dao
interface DiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<Result>)

    @Query("SELECT * FROM results ORDER BY popularity DESC")
    fun getAll(): LiveData<List<Result>>

    @Query("DELETE FROM results")
    fun deleteAll()

}