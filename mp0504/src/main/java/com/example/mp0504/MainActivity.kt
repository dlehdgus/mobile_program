package com.example.mp0504

import android.os.Bundle
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var tbObj: ToggleButton
    lateinit var ivObj1: ImageView
    lateinit var ivObj2: ImageView
    lateinit var ivObj3: ImageView
    lateinit var ivObj: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tbObj = findViewById(R.id.toggleButton)
        ivObj1 = findViewById(R.id.imageView1)
        ivObj2 = findViewById(R.id.imageView2)
        ivObj3 = findViewById(R.id.imageView3)
        ivObj = findViewById(R.id.imageView)

        // 토글 버튼 상태(ON/OFF)에 맞춰 조명 및 배경 이미지 전환
        tbObj.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Toggle ON -> 조명 켜짐 이미지 및 밝은 배경(bg2)으로 변경
                ivObj1.setImageResource(R.drawable.light21)
                ivObj2.setImageResource(R.drawable.light22)
                ivObj3.setImageResource(R.drawable.light23)
                ivObj.setImageResource(R.drawable.bg2)
            } else {
                // Toggle OFF -> 조명 꺼짐 이미지 및 어두운 배경(bg1)으로 변경
                ivObj1.setImageResource(R.drawable.light11)
                ivObj2.setImageResource(R.drawable.light12)
                ivObj3.setImageResource(R.drawable.light13)
                ivObj.setImageResource(R.drawable.bg1)
            }
        }
    }
}