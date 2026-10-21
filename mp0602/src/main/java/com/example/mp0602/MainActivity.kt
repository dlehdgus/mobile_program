package com.example.mp0602

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// 첫 번째 액티비티: 그림 선택 후 GameActivity 로 데이터 전달(단방향)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val rbCat: RadioButton = findViewById(R.id.rbCat)
        val rbDog: RadioButton = findViewById(R.id.rbDog)
        val btnStart: Button = findViewById(R.id.btnStart)

        // 그림을 눌러도 라디오 버튼이 선택되도록
        findViewById<ImageView>(R.id.imgCat).setOnClickListener { rbCat.isChecked = true }
        findViewById<ImageView>(R.id.imgDog).setOnClickListener { rbDog.isChecked = true }

        btnStart.setOnClickListener(View.OnClickListener {
            val animal = if (radioGroup.checkedRadioButtonId == R.id.rbDog) "dog" else "cat"

            val intent = Intent(this, GameActivity::class.java)  // ① 인텐트 객체 생성
            intent.putExtra("animal", animal)                    // ② 선택한 그림 데이터 추가
            startActivity(intent)                                // ③ 액티비티 호출
        })
    }
}