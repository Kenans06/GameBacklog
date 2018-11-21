package com.example.gebruiker.gamebacklog

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.add_game.*
import java.text.SimpleDateFormat
import java.util.*


class AddGame : AppCompatActivity(), OnItemSelectedListener {

    private var mStatus: String? = null
    private var model: GameViewModel? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_game_container)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButtonSave)

        model = ViewModelProviders.of(this).get(GameViewModel::class.java)


        fab.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextPlatform.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val game = Game("", "", "", "", "")
                game.title = editTextTitle.text.toString()
                game.platform = editTextPlatform.text.toString()
                game.notes = editTextNotes.text.toString()
                game.datum = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
                game.status = mStatus!!

                model!!.insert(game)

            }

            finish()
        }

        spinner.onItemSelectedListener = this

        val categories = ArrayList<String>()
        categories.add("Want to play")
        categories.add("Playing")
        categories.add("Stalled")
        categories.add("Dropped")

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        mStatus = parent.getItemAtPosition(position).toString()

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        // TODO Auto-generated method stub
    }

}



