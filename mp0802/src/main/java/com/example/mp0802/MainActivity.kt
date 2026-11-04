package com.example.mp0802

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var ivQestion: ImageView
    lateinit var ivAnswer: ImageView
    lateinit var ivAmPm: ImageView
    //질문 및 AM/PM 상태 변수
    var question = 0
    var ampm = 0
    //문제 이미지 배열
    var drawableQuestion = intArrayOf(
        R.drawable.question1, R.drawable.question2, R.drawable.question3,
        R.drawable.question4, R.drawable.question5
    )
    //AM/PM 답변 이미지 배열
    var drawableAnswerAM = intArrayOf(
        R.drawable.answer11, R.drawable.answer12, R.drawable.answer13,
        R.drawable.answer14, R.drawable.answer15
    )
    var drawableAnswerPM = intArrayOf(
        R.drawable.answer21, R.drawable.answer22, R.drawable.answer23,
        R.drawable.answer24, R.drawable.answer25
    )
    //AM/PM 시간 배열
    var timeAnswerAM = arrayOf(
        "9:15", "2:25", "10:35", "11:40", "3:10"
    )
    var timeAnswerPM = arrayOf(
        "21:15", "14:25", "22:35", "23:40", "15:10"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ivQestion = findViewById(R.id.question)
        ivAnswer = findViewById(R.id.answer)
        ivAmPm = findViewById(R.id.ampm)
        newQuestion()
        val buttonStart: Button = findViewById(R.id.buttonStart)
        //Start 버튼 클릭 리스너 설정
        buttonStart.setOnClickListener {
            newQuestion()
        }
        val buttonTime: Button = findViewById(R.id.buttonTime)
        //Time 버튼 클릭 리스너 설정
        buttonTime.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                checkAnswer(i.toString() + ":" + i2.toString())
            }
            //TimePickerDialog 생성
            val timePickerDialog = TimePickerDialog(this, R.style.TimePickerStyle, listener, hour,
                minute, true)
            timePickerDialog.show()    //TimePickerDialog 활성화
        }
    }
    //질문 및 AM/PM 상태 무작위 선택
    private fun newQuestion() {
        question = Random.nextInt(drawableQuestion.size)
        ampm = Random.nextInt(2)
        //질문 이미지 설정
        ivQestion.setImageResource(drawableQuestion[question])
        //AM/PM 상태에 따라 표시 이미지 설정
        if (ampm == 0) {
            ivAmPm.setImageResource(R.drawable.time_am)
        } else {
            ivAmPm.setImageResource(R.drawable.time_pm)
        }
        ivAnswer.isVisible = false
    }
    //선택한 시간이 현재 문제의 AM/PM 정답과 일치하는지 확인
    private fun checkAnswer(resultTime: String) {
        val answerTime = if (ampm == 0) timeAnswerAM[question] else timeAnswerPM[question]
        if (resultTime == answerTime) {
            val answerImage = if (ampm == 0) drawableAnswerAM[question] else drawableAnswerPM[question]
            ivAnswer.setImageResource(answerImage)
            ivAnswer.isVisible = true
            Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_LONG).show()
        } else {
            ivAnswer.isVisible = false
            Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_LONG).show()
        }
    }
}
