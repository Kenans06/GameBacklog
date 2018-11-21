package com.example.gebruiker.gamebacklog

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import com.example.gebruiker.gamebacklog.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    fun getAll(): LiveData<List<Game>>

    @Delete
    fun delete(game: Game)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(game: Game)

    @Insert(onConflict = IGNORE)
    fun insert(title: Game)

    @Query("DELETE from game_table")
    fun deleteAll()
}