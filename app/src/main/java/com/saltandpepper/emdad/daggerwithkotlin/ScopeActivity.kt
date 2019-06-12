package com.saltandpepper.emdad.daggerwithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scope.*
import javax.inject.Inject
import javax.inject.Scope

 var staticCounter = 0;

private lateinit var magicBox: DaggerScopeActivity_MagicBox
class ScopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope)
        magicBox = DaggerScopeActivity_MagicBox.create() as DaggerScopeActivity_MagicBox

        button.setOnClickListener {
            val storage = Storage()
            magicBox.poke(storage)
            textView.text = "Unique ${storage.uniqueMagic.count}" + " " +
                    "\nNormal  ${storage.normalMagic.count}"
        }




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
        @Inject lateinit var uniqueMagic: UniqueMagic
        @Inject lateinit var normalMagic: NormalMagic
    }

    @MagicScope
    @Component
    interface MagicBox{
        fun poke(storage:Storage)
    }


}
