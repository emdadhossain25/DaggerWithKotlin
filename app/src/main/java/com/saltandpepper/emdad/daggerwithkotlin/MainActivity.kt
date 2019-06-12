package com.saltandpepper.emdad.daggerwithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var test:Info



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainActivity_MagicBox
                .create()
                .poke(this)
        textview.text = test.text
    }

    class Info @Inject constructor(var text:String){

    }

    @Component(modules = [Bag::class])
    interface MagicBox{
        fun poke(test:MainActivity)
    }

    @Module
    class Bag{

        @Provides
        fun providesInfo():Info{
            return Info("Love Dagger2");
        }
    }

}
