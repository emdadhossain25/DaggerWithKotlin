package com.saltandpepper.emdad.daggerwithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var  info: Info
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainActivity_MagicBox.create().
                poke(this)
        textview.text = info.text
    }

     class Info @Inject constructor() {
        val text = "Welcome Dagger 2"
    }

    @Component
    interface MagicBox {
        fun poke(app: MainActivity)
    }
}
