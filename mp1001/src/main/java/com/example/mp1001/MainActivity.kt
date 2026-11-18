package com.example.mp1001

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var ivNumA: ImageView
    lateinit var ivNumB: ImageView
    lateinit var ivBackground: ImageView
    lateinit var etAnswer: EditText
    lateinit var btnReset: Button
    lateinit var btnResult: Button
    lateinit var tvMessage: TextView
    //숫자 이미지 배열 (0~9)
    var numberImages = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2,
        R.drawable.num3, R.drawable.num4, R.drawable.num5,
        R.drawable.num6, R.drawable.num7, R.drawable.num8,
        R.drawable.num9
    )
    var correctCount: Int = 0 //정답 개수
    var totalCount: Int = 0 //문제 개수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnReset = findViewById(R.id.button_reset)
        btnResult = findViewById(R.id.button_result)
        etAnswer = findViewById(R.id.editText_answer)
        tvMessage = findViewById(R.id.textView_message)
        ivBackground = findViewById(R.id.imageView_bg)
        loadGameStats()                       //SharedPreferences로부터 저장된 데이터 불러오기
        var result = generateQuestion()       //첫 문제 출제
        //다시시작하기 버튼 클릭 이벤트
        btnReset.setOnClickListener {
            val pref: SharedPreferences = getSharedPreferences("gamefile", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.clear()                    //모든 데이터 삭제
            editor.apply()
            loadGameStats()                   //통계 초기화
            ivBackground.setImageResource(R.drawable.bg_quiz)
            etAnswer.text.clear()
        }
        //결과 확인하기 버튼 클릭 이벤트
        btnResult.setOnClickListener {
            totalCount += 1                   //문제 수 추가
            val answer = etAnswer.text.toString().toIntOrNull()
            //정답 판별
            if (answer == result) {
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                correctCount += 1
                ivBackground.setImageResource(R.drawable.bg_ok)
            } else {
                Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                ivBackground.setImageResource(R.drawable.bg_no)
            }
            //SharedPreferences에 결과 저장
            val pref: SharedPreferences = getSharedPreferences("gamefile", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("total", totalCount)
            editor.putInt("grade", correctCount)
            editor.apply()
            //새로운 문제 출제 및 UI 업데이트
            loadGameStats()
            result = generateQuestion()
            etAnswer.text.clear()
        }
    }

    //곱셈 문제 생성 메서드
    fun generateQuestion(): Int {
        val partA = (Math.random() * 9).roundToInt()
        val partB = (Math.random() * 9).roundToInt()
        val result = partA * partB
        ivNumA = findViewById(R.id.imageView_num1)
        ivNumB = findViewById(R.id.imageView_num2)
        ivNumA.setImageResource(numberImages[partA])
        ivNumB.setImageResource(numberImages[partB])
        return result
    }
    //SharedPreferences로부터 저장된 게임 통계 불러오기
    fun loadGameStats() {
        val pref: SharedPreferences = getSharedPreferences("gamefile", Context.MODE_PRIVATE)
        totalCount = pref.getInt("total", 0)
        correctCount = pref.getInt("grade", 0)
        if (totalCount > 0) {
            val percent = (correctCount.toDouble() / totalCount.toDouble()) * 100
            tvMessage.text = "당신의 정답율은 %.2f (%d/%d) %% 입니다".format(percent, correctCount,
                totalCount)
        } else {
            tvMessage.text = "당신의 정답율은 0% 입니다"
        }
    }

}