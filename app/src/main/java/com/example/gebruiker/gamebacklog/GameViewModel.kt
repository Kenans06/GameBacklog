package com.example.gebruiker.gamebacklog

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.gebruiker.gamebacklog.Game
import com.example.gebruiker.gamebacklog.GameRepository
import com.example.gebruiker.gamebacklog.GameRoomDatabase
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: GameRepository
    val allGames: LiveData<List<Game>>

    init {
        val gameDao = GameRoomDatabase.getDatabase(application, scope).gameDao()
        repository = GameRepository(gameDao)
        allGames = repository.allGames
    }

    fun insert(game: Game) = scope.launch(Dispatchers.IO) {
        repository.insert(game)
    }

    fun delete(game: Game) {
        repository.delete(game)
    }

    fun updateGame(game: Game) = scope.launch(Dispatchers.IO) {
        repository.updateGame(game)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}