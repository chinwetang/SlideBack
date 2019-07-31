package com.chinwe.sideslipback

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chinwe.lib_slide_back.internal.CustomSlideBack

class MainActivity : AppCompatActivity() ,CustomSlideBack{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun loot() =false

    override fun damp()=0.3f
}
