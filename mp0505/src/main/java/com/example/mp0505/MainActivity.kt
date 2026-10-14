package com.example.mp0505

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
    lateinit var sObj1: Switch    // 고양이 스위치
    lateinit var sObj2: Switch    // 라이트(조명) 스위치
    lateinit var tvObj: TextView
    lateinit var ivObj1: ImageView   // 고양이 이미지뷰
    lateinit var ivObj2: ImageView   // 조명 이미지뷰

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sObj1 = findViewById(R.id.switch1)
        sObj2 = findViewById(R.id.switch2)
        sObj2.textOn = ""
        sObj2.textOff = ""
        tvObj = findViewById(R.id.textView2)
        ivObj1 = findViewById(R.id.imageView1) // 고양이
        ivObj2 = findViewById(R.id.imageView2) // 조명

        // 1. '고양이' 스위치 이벤트 처리 (cat5)
        sObj1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // ON: 창문에 고양이가 나타남
                ivObj1.visibility = View.VISIBLE
            } else {
                // OFF: 고양이가 숨겨짐
                ivObj1.visibility = View.INVISIBLE
            }
        }

        // 2. '라이트' 스위치 이벤트 처리 (light31, light32)
        sObj2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // ON: 화면 속 조명이 켜짐 (light32)
                ivObj2.setImageResource(R.drawable.light32)
            } else {
                // OFF: 조명이 꺼짐 (light31)
                ivObj2.setImageResource(R.drawable.light31)
            }
        }
    }
}