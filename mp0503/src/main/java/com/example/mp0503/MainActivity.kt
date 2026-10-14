package com.example.mp0503

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var rbObj1: RadioButton
    lateinit var rbObj2: RadioButton
    lateinit var rbObj3: RadioButton
    lateinit var rbObj4: RadioButton
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

        rbObj1 = findViewById(R.id.radioButton1)
        rbObj2 = findViewById(R.id.radioButton2)
        rbObj3 = findViewById(R.id.radioButton3)
        rbObj4 = findViewById(R.id.radioButton4)
        ivObj = findViewById(R.id.imageView)

        // RadioButton 선택 변경 이벤트 처리
        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.radioButton2 -> {
                        // 4번 버튼이 정답
                        Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // 1, 2, 3번 버튼은 오답
                        Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        rbObj1.setOnCheckedChangeListener(listener)
        rbObj2.setOnCheckedChangeListener(listener)
        rbObj3.setOnCheckedChangeListener(listener)
        rbObj4.setOnCheckedChangeListener(listener)
    }
}