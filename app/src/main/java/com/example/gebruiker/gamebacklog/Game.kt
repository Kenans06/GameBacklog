package com.example.gebruiker.gamebacklog

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game(@ColumnInfo(name = "title") var title: String,
                @ColumnInfo(name = "platform") var platform: String,
                @ColumnInfo(name = "status") var status: String,
                @ColumnInfo(name = "datum") var datum: String,
                @ColumnInfo(name = "notes") var notes: String)
{
    @PrimaryKey(autoGenerate = true)
    var gameId: Int = 0

}





