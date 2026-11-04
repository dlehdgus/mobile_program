package com.example.mp0803

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvInput: TextView
    private var tvResultCheck = false
    private var tvInputCheck = false
    //직전에 = 버튼을 눌렀는지 확인
    private var equalCheck = false
    //계산에 필요한 숫자 변수들
    private var FirstNum: Double = 0.0
    private var SecondNum: Double = 0.0
    private var ResultNum: Double = 0.0
    private var ArithOper: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button: Button = findViewById(R.id.button)
        //버튼 클릭 리스너 설정
        button.setOnClickListener {
            //대화상자 객체 생성
            val builder = AlertDialog.Builder(this)
            //대화상자 레이아웃 설정
            val dialogView = layoutInflater.inflate(R.layout.dialog_calculate, null)
            builder.setView(dialogView)
            //대화상자 활성화
            val dialog = builder.show()
            tvResult = dialogView.findViewById(R.id.tvResult)
            tvInput = dialogView.findViewById(R.id.tvIntput)
            //대화상자를 다시 열면 이전 계산을 지우고 시작
            resetCalculator()
            val buttonclose: Button = dialogView.findViewById(R.id.buttonclose)
            //대화상자 닫기 버튼 클릭 리스너
            buttonclose.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
    //계산기 상태 초기화
    private fun resetCalculator() {
        tvResult.text = ""
        tvInput.text = ""
        tvResultCheck = false
        tvInputCheck = false
        equalCheck = false
        FirstNum = 0.0
        SecondNum = 0.0
        ArithOper = null
    }
    //= 버튼 다음에 숫자를 입력하면 새로운 계산을 시작
    private fun startNewInput() {
        if (equalCheck) {
            tvInput.text = ""
            tvInputCheck = false
            equalCheck = false
            ArithOper = null
        }
    }
    //정수는 소수점 없이 문자열로 변환
    private fun numToString(num: Double): String {
        return if (num % 1.0 == 0.0 && abs(num) < 1e15) num.toLong().toString()
        else num.toString()
    }
    //선택한 연산자로 계산, 0으로 나누면 false 반환
    private fun calculate(): Boolean {
        if (ArithOper == "÷" && SecondNum == 0.0) {
            Toast.makeText(this, "0으로 나눌 수 없습니다", Toast.LENGTH_SHORT).show()
            return false
        }
        when (ArithOper) {
            "+" -> ResultNum = FirstNum + SecondNum
            "-" -> ResultNum = FirstNum - SecondNum
            "x" -> ResultNum = FirstNum * SecondNum
            else -> ResultNum = FirstNum / SecondNum
        }
        ResultNum = round(ResultNum * 1000000000) / 1000000000
        return true
    }
    //숫자 버튼 클릭 시 동작하는 메서드
    fun numClick(view: View) {
        startNewInput()
        if (!tvResultCheck || tvResult.text.toString() == "0") {
            tvResult.text = ""
        }
        tvResult.append(view.tag?.toString())
        tvResultCheck = true
        val str = tvResult.text.toString()
        SecondNum = str.toDouble()
    }
    //백스페이스 버튼 클릭 시 동작하는 메서드
    fun BackClick(view: View) {
        if (!tvResultCheck) return
        val str = tvResult.text.toString().dropLast(1)
        tvResult.text = str
        if (str.isEmpty()) {
            SecondNum = 0.0
            tvResultCheck = false
        } else {
            SecondNum = str.toDouble()
        }
    }
    //Clear(초기화) 버튼 클릭 시 동작하는 메서드
    fun ClearClick(view: View) {
        tvResult.text = ""
        tvResultCheck = false
        SecondNum = 0.0
    }
    //AC(전체 초기화) 버튼 클릭 시 동작하는 메서드
    fun ACClick(view: View) {
        resetCalculator()
    }
    //소수점 버튼 클릭 시 동작하는 메서드
    fun DotClick(view: View) {
        startNewInput()
        if (!tvResultCheck) {
            tvResult.text = "0."
            tvResultCheck = true
            SecondNum = 0.0
        } else if (!tvResult.text.contains(".")) {
            tvResult.append(".")
        }
    }
    //연산자 버튼 클릭 시 동작하는 메서드
    fun OperClick(view: View) {
        val btn = view.tag?.toString()
        equalCheck = false
        if (!tvInputCheck) {
            tvInput.text = numToString(SecondNum) + " " + btn
            FirstNum = SecondNum
            tvInputCheck = true
            tvResultCheck = false
        } else {
            if (!tvResultCheck) {
                tvInput.text = numToString(FirstNum) + " " + btn
            } else {
                if (!calculate()) return
                tvInput.text = numToString(ResultNum) + " " + btn
                tvResultCheck = false
                FirstNum = ResultNum
            }
        }
        ArithOper = btn
    }
    //결과 버튼 클릭 시 동작하는 메서드
    fun EqualClick(view: View) {
        //연산자를 고르기 전에는 계산하지 않음
        if (ArithOper == null) return
        if (!calculate()) return
        tvInput.text = numToString(FirstNum) + " " + ArithOper + " " +
            numToString(SecondNum) + " = " + numToString(ResultNum)
        FirstNum = ResultNum
        tvResultCheck = false
        equalCheck = true
    }
}
