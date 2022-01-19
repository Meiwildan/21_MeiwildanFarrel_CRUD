package com.example.a21_meiwildanfarrel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        val intentButton: Button =findViewById(R.id.add)
        intentButton.setOnClickListener{viewDetail()
        }
    }

    private fun viewDetail() {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}