package com.example.gebruiker.gamebacklog

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyGameRecyclerAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<MyGameRecyclerAdapter.GameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var games = emptyList<Game>() // Cached copy of words

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.textViewTitle)
        val platformItemView: TextView = itemView.findViewById(R.id.textViewPlatform)
        val statusItemView: TextView = itemView.findViewById(R.id.textViewStatus)
        val datumItemView: TextView = itemView.findViewById(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = inflater.inflate(R.layout.recylerview_grid, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val current = games[position]

        holder.titleItemView.text = current.title
        holder.statusItemView.text = current.status
        holder.datumItemView.text = current.datum
        holder.platformItemView.text = current.platform

    }

    internal fun setGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    override fun getItemCount() = games.size
}