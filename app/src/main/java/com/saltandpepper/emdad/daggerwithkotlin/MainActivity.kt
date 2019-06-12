package com.saltandpepper.emdad.daggerwithkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Qualifier

const val LOVE ="Love"
const val HELLO ="Hello"

class MainActivity : AppCompatActivity() {

    @Inject @field:Choose(LOVE) lateinit var infoLove:Info
    @Inject @field:Choose(HELLO) lateinit var infoHello:Info

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainActivity_MagicBox
                .create()
                .poke(this)
        textview.text = "${infoLove.text} ${infoHello.text}"
        var intent:Intent = Intent(this,ScopeActivity::class.java)
        startActivity(intent)

    }

    class Info @Inject constructor(var text:String){

    }

    @Component(modules = [Bag::class])
    interface MagicBox{
        fun poke(test:MainActivity)
    }

    @Module
    class Bag{

        @Provides @Choose(LOVE)
        fun sayLoveDagger2():Info{
            return Info("Love Dagger2");
        }

        @Provides @Choose(HELLO)
        fun sayHelloDagger2():Info{
            return Info("Hello Dagger2");
        }
    }


    @Qualifier
    @MustBeDocumented
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class Choose(val value:String = "")
}
