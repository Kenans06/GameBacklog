package com.example.gebruiker.gamebacklog

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.add_game.*
import java.text.SimpleDateFormat
import java.util.*

class GameView : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var mStatus: String? = null
    private var mPosition: String? = null
    private var mSpinnerPosition: Int? = null
    private var mDataAdapter: ArrayAdapter<String>? = null
    private var mCurrentGame: Game? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_game_container)

        val mGameViewModel: GameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        mPosition = intent.getStringExtra("position")

        mGameViewModel.allGames.observe(this, Observer {
            mCurrentGame = mGameViewModel.allGames.value!![mPosition!!.toInt()]
            editTextTitle.setText(mCurrentGame!!.title)
            editTextPlatform.setText(mCurrentGame!!.title)
            editTextPlatform.setText(mCurrentGame!!.title)

        })


        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButtonSave)
        fab.setOnClickListener {

            val replyIntent = Intent()
            if (mCurrentGame == null) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                mCurrentGame!!.title = editTextTitle.text.toString()
                mCurrentGame!!.platform = editTextPlatform.text.toString()
                mCurrentGame!!.notes = editTextNotes.text.toString()
                mCurrentGame!!.datum = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
                mCurrentGame!!.status = mStatus!!

                mGameViewModel.updateGame(mCurrentGame!!)
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }

        // Spinner click listener
        spinner.onItemSelectedListener = this

        val categories = ArrayList<String>()
        categories.add("Want to play")
        categories.add("Playing")
        categories.add("Stalled")
        categories.add("Dropped")

        mDataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        mDataAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = mDataAdapter

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        mStatus = parent.getItemAtPosition(position).toString()
        mSpinnerPosition = position

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        // TODO Auto-generated method stub
    }
}



