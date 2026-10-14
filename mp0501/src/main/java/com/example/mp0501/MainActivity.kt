package com.example.mp0501

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btObj1: ImageButton
    lateinit var btObj2: ImageButton
    lateinit var btObj3: ImageButton
    lateinit var btObj4: ImageButton
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

        btObj1 = findViewById(R.id.button1)
        btObj2 = findViewById(R.id.button2)
        btObj3 = findViewById(R.id.button3)
        btObj4 = findViewById(R.id.button4)
        ivObj = findViewById(R.id.imageView2)

        // 1번 버튼 (무당벌레) -> 틀렸습니다 + 달팽이 등에 무당벌레 껍데기 적용
        btObj1.setOnClickListener {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            ivObj.setImageResource(R.drawable.answer1)
            ivObj.visibility = View.VISIBLE
        }

        // 2번 버튼 (달팽이 껍데기 - 정답) -> 맞았습니다 + 달팽이 집 완성
        btObj2.setOnClickListener {
            Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
            ivObj.setImageResource(R.drawable.answer2)
            ivObj.visibility = View.VISIBLE
        }

        // 3번 버튼 (축구공) -> 틀렸습니다 + 달팽이 등에 축구공 적용
        btObj3.setOnClickListener {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            ivObj.setImageResource(R.drawable.answer3)
            ivObj.visibility = View.VISIBLE
        }

        // 4번 버튼 (거북이 등) -> 틀렸습니다 + 달팽이 등에 거북이 등 적용
        btObj4.setOnClickListener {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            ivObj.setImageResource(R.drawable.answer4)
            ivObj.visibility = View.VISIBLE
        }
    }
}