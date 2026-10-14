package com.example.mp0502

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var cbObj1: CheckBox
    lateinit var cbObj2: CheckBox
    lateinit var cbObj3: CheckBox
    lateinit var cbObj4: CheckBox
    lateinit var btnObj: Button
    lateinit var tvObj: TextView
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cbObj1 = findViewById(R.id.checkBox1)
        cbObj2 = findViewById(R.id.checkBox2)
        cbObj3 = findViewById(R.id.checkBox3)
        cbObj4 = findViewById(R.id.checkBox4)
        btnObj = findViewById(R.id.button)
        tvObj = findViewById(R.id.textViewResult)

        // 1. 체크박스를 체크/해제할 때마다 해당 물품 이름을 Toast로 출력[cite: 6, 65, 66]
        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val itemName = when (buttonView.id) {
                R.id.checkBox1 -> "소방차"
                R.id.checkBox2 -> "청진기"
                R.id.checkBox3 -> "소화기"
                R.id.checkBox4 -> "장갑"
                else -> ""
            }

            if (isChecked) {
                Toast.makeText(applicationContext, "$itemName 선택했습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "$itemName 해제했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        cbObj1.setOnCheckedChangeListener(listener)
        cbObj2.setOnCheckedChangeListener(listener)
        cbObj3.setOnCheckedChangeListener(listener)
        cbObj4.setOnCheckedChangeListener(listener)

        // 2. 결과보기 버튼 클릭 이벤트 처리
        btnObj.setOnClickListener {
            checkAnswer()
        }
    }

    // XML의 android:onClick="onClickChoice" 속성과 연결되는 함수[cite: 27, 29]
    fun onClickChoice(view: View) {
        checkAnswer()
    }

    // 정답 및 선택 상태 판별 공통 로직
    private fun checkAnswer() {
        val is1 = cbObj1.isChecked // 소방차 (정답)
        val is2 = cbObj2.isChecked // 청진기
        val is3 = cbObj3.isChecked // 소화기 (정답)
        val is4 = cbObj4.isChecked // 장갑

        // 선택된 물건의 이름을 Toast로 출력
        var selected = ""
        if (is1) selected += "소방차 "
        if (is2) selected += "청진기 "
        if (is3) selected += "소화기 "
        if (is4) selected += "장갑 "

        if (selected.isNotEmpty()) {
            Toast.makeText(applicationContext, "${selected.trim()} 선택했습니다.", Toast.LENGTH_SHORT).show()
        }

        // 정답 판별 (소방차와 소화기만 선택했을 때 정답)
        if (is1 && !is2 && is3 && !is4) {
            tvObj.text = "맞았습니다"
        } else {
            tvObj.text = "틀렸습니다"
        }
    }
}