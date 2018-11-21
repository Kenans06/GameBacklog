package com.example.gebruiker.gamebacklog

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.gebruiker.gamebacklog.Game
import com.example.gebruiker.gamebacklog.GameDao

class GameRepository(private val gameDao: GameDao) {

    val allGames: LiveData<List<Game>> = gameDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }

    @WorkerThread
    fun delete(game: Game) {
        gameDao.delete(game)
    }


    @WorkerThread
    fun updateGame(game: Game) {
        gameDao.update(game)
    }


}