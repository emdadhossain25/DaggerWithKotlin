package com.saltandpepper.emdad.daggerwithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Subcomponent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scope.*
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

var staticCounter = 0;


private lateinit var mainBox:DaggerScopeActivity_SingleToneBox
private lateinit var magicBox: ScopeActivity.MagicBox

class ScopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope)
        mainBox = DaggerScopeActivity_SingleToneBox.create() as DaggerScopeActivity_SingleToneBox
        magicBox = mainBox.getMagicBox()

        button.setOnClickListener {
            val storage = Storage()
            magicBox = mainBox.getMagicBox()
            useStorage()
            textView.text = "Unique ${storage.uniqueMagic.count}" + " " +
                    "\nNormal  ${storage.normalMagic.count}"
        }




    }

    @Singleton
    class SingleTone @Inject constructor(){
        val count = staticCounter++
    }
    @MagicScope
    class UniqueMagic @Inject constructor(){
        val count = staticCounter++
    }

    class NormalMagic @Inject constructor(){
        val count = staticCounter++
    }

    @Scope
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MagicScope


    class Storage{
        @Inject lateinit var singletonOne:SingleTone
        @Inject lateinit var uniqueMagic: UniqueMagic
        @Inject lateinit var normalMagic: NormalMagic
    }


    @Singleton
    @Component
    interface SingleToneBox{
        fun getMagicBox():MagicBox
    }

    @MagicScope
    @Subcomponent
    interface MagicBox{
        fun poke(storage:Storage)
    }

    private fun useStorage() {
        val storage = Storage()
        magicBox.poke(storage)
        textView.text =
                "\nSingletonOne ${storage.singletonOne.count} " +
                "\nUniqueMagic ${storage.uniqueMagic.count}" +
                "\nNormalMagic ${storage.normalMagic.count} "
    }
}
