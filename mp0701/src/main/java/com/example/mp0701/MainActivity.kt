package com.example.mp0701

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var ivObjA: ImageView
    lateinit var ivObjB: ImageView
    lateinit var ivObjOp: ImageView
    lateinit var ivObjResult: ImageView
    lateinit var ivObjResult0: ImageView
    var number = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3,
        R.drawable.num4, R.drawable.num5, R.drawable.num6, R.drawable.num7,
        R.drawable.num8, R.drawable.num9
    )
    var btnNumber = intArrayOf(
        R.drawable.btn0, R.drawable.btn1, R.drawable.btn2, R.drawable.btn3,
        R.drawable.btn4, R.drawable.btn5, R.drawable.btn6, R.drawable.btn7,
        R.drawable.btn8, R.drawable.btn9
    )
    var buttonChoice = intArrayOf(R.id.buttonChoice1, R.id.buttonChoice2, R.id.buttonChoice3)
    var btnObjChoice : Array<ImageButton?> = arrayOfNulls<ImageButton>(3)
    var operator = "+"
    var question = 0
    var select : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Calulate("+")
    }

    fun Calulate(operator:String) {
        var partA = (Math.random() * 9).roundToInt()
        var partB = (Math.random() * 9).roundToInt()
        ivObjOp = findViewById(R.id.imageViewOp)
        var result = 0
        // 연산자에 따른 계산
        when (operator) {
            "+" -> {
                result = partA + partB
                ivObjOp.setImageResource(R.drawable.op1)
            }
            "-" -> {
                result = partA - partB
                ivObjOp.setImageResource(R.drawable.op2)
            }
            "*" -> {
                result = partA * partB
                ivObjOp.setImageResource(R.drawable.op3)
            }
            "%" -> {
                if (partA == 0) partA = 1
                if (partB == 0) partB = 1
                if (partA < partB) {
                    val swap = partA
                    partB = partA
                    partA = swap
                }
                result = partA % partB
                ivObjOp.setImageResource(R.drawable.op4)
            }
        }
        question = partB

        // 틀린 선택지 생성
        var wrongAnswer1 = partB - 1
        if (wrongAnswer1 <0 ) wrongAnswer1 =9
        var wrongAnswer2 = partB + 1
        if (wrongAnswer2 > 9) wrongAnswer2 = 0
        ivObjA = findViewById(R.id.imageViewA)
        ivObjB = findViewById(R.id.imageViewB)
        ivObjResult0 = findViewById(R.id.imageViewResult0)
        ivObjResult = findViewById(R.id.imageViewResult)

        // 이미지 설정
        ivObjA.setImageResource(number[partA])
        ivObjResult0.visibility = View.VISIBLE

        // 선택 결과에 따라 레이아웃 설정
        if (result < 0 ) ivObjResult0.setImageResource(R.drawable.op2)
        else if (result < 10) ivObjResult0.visibility = View.GONE
        else ivObjResult0.setImageResource(number[result / 10])
        if (result < 0 ) ivObjResult.setImageResource(number[(result*-1) % 10])
        else ivObjResult.setImageResource(number[result % 10])

        // 정답 선택지 설정
        val index = (Math.random() * 3).toInt()
        var index2 = index + 1
        if (index2 == 3) index2 = 0
        var index3 = index2 + 1
        if (index3 == 3) index3 = 0
        select = index

        // 버튼에 이벤트 리스너 설정
        btnObjChoice[index] = findViewById(buttonChoice[index])
        btnObjChoice[index2] = findViewById(buttonChoice[index2])
        btnObjChoice[index3] = findViewById(buttonChoice[index3])

        // 클릭 리스너 설정
        btnObjChoice[index]?.setOnClickListener(this)
        btnObjChoice[index2]?.setOnClickListener(this)
        btnObjChoice[index3]?.setOnClickListener(this)

        // 버튼 이미지 설정
        btnObjChoice[index]?.setBackgroundResource(btnNumber[partB])
        btnObjChoice[index2]?.setBackgroundResource(btnNumber[wrongAnswer1])
        btnObjChoice[index3]?.setBackgroundResource(btnNumber[wrongAnswer2])
    }

    // 버튼 클릭 시 정답 체크
    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonChoice1 -> if (buttonChoice[select]==R.id.buttonChoice1)
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
            else Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
            R.id.buttonChoice2 -> if (buttonChoice[select]==R.id.buttonChoice2)
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
            else Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
            R.id.buttonChoice3 -> if (buttonChoice[select]==R.id.buttonChoice3)
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
            else Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }

    // 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 메뉴 항목 선택 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item01 -> {
                Calulate("+")
                return true
            }
            R.id.item02 -> {
                Calulate("-")
                return true
            }
            R.id.item03 -> {
                Calulate("*")
                return true
            }
            R.id.item04 -> {
                Calulate("%")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}