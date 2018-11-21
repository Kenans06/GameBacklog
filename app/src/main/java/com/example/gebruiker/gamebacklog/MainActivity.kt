package com.example.gebruiker.gamebacklog

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val newGameActivityRequestCode = 1
    private lateinit var mGameViewModel: GameViewModel
    private var mAdapter: MyGameRecyclerAdapter? = null
    private var mPosition: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddGame::class.java)
            startActivityForResult(intent, newGameActivityRequestCode)
        }

        mAdapter = MyGameRecyclerAdapter(this)

        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        mGameViewModel.allGames.observe(this, Observer { Games ->
            Games?.let { mAdapter!!.setGames(it) }
        })

        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(this@MainActivity, GameView::class.java)
                intent.putExtra("position", mPosition.toString())
                startActivity(intent)

            }
        })

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View?) {
                view?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                view?.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    mPosition = holder.adapterPosition
                    onClickListener.onItemClicked(holder.adapterPosition, view)

                }
            }
        })
    }


    private val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    mPosition = viewHolder.adapterPosition
                    mGameViewModel.delete(mGameViewModel.allGames.value!![mPosition!!])
                    mAdapter!!.notifyItemRemoved(mPosition!!)


                }
            }

}

