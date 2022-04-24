package com.coco.chess.chessview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coco.chess.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class plainactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plainactivity)
        val intent = Intent(this,homescreen::class.java)
     GlobalScope.launch {
         delay(3000)
         startActivity(intent)
         finish()

     }
    }
}