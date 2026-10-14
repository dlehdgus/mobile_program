package com.example.mp0505

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit	var	sObj1: Switch
    lateinit	var	sObj2:	Switch
    lateinit	var	tvObj: TextView
    lateinit	var	ivObj1: ImageView
    lateinit	var	ivObj2:	ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sObj1=findViewById(R.id.switch1)
        sObj2=findViewById(R.id.switch2)
        sObj2.textOn = ""
        sObj2.textOff = ""
        tvObj=findViewById(R.id.textView2)
        ivObj1=findViewById(R.id.imageView1)
        ivObj2=findViewById(R.id.imageView2)

        sObj1.setOnCheckedChangeListener{	buttonView,	isChecked->
            // TODO : event handler
        }
        sObj2.setOnCheckedChangeListener{	buttonView,		isChecked->
            // TODO  event handler
        }
    }
}