package com.example.mp0603

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// 첫 번째 액티비티: 도형 선택 → GameActivity 실행 → 결과 받아서 출력 (양방향)
class MainActivity : AppCompatActivity() {

    lateinit var launcher: ActivityResultLauncher<Intent>

    // 각 도형에 해당하는 정답 그림
    private val circleAnswers = setOf(R.drawable.shape03, R.drawable.shape04, R.drawable.shape08) // 사탕, 공, 도넛
    private val squareAnswers = setOf(R.drawable.shape02, R.drawable.shape06, R.drawable.shape09) // 크래커, 책, 창문

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
        val rbCircle: RadioButton = findViewById(R.id.rbCircle)
        val rbSquare: RadioButton = findViewById(R.id.rbSquare)
        val tvResult: TextView = findViewById(R.id.tvResult)
        val results = arrayOf<ImageView>(
            findViewById(R.id.result1), findViewById(R.id.result2), findViewById(R.id.result3)
        )

        findViewById<ImageView>(R.id.imgCircle).setOnClickListener { rbCircle.isChecked = true }
        findViewById<ImageView>(R.id.imgSquare).setOnClickListener { rbSquare.isChecked = true }

        // ⑦ 결과를 받을 콜백 등록
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                // ⑧ 전달된 데이터 받기
                val intent = result.data
                val shape = intent?.getStringExtra("shape") ?: "circle"
                val selected = intent?.getIntArrayExtra("selected") ?: intArrayOf()

                // 선택한 그림 출력
                for (i in results.indices) {
                    if (i < selected.size) results[i].setImageResource(selected[i])
                    else results[i].setImageDrawable(null)
                }

                // 정답 확인 : 3개를 골랐고, 모두 선택한 도형과 같은 모양이면 정답
                val answers = if (shape == "square") squareAnswers else circleAnswers
                val correct = selected.size == 3 && selected.all { it in answers }
                tvResult.text = if (correct) "맞았습니다" else "틀렸습니다"
            }
        }

        findViewById<Button>(R.id.btnStart).setOnClickListener(View.OnClickListener {
            val shape = if (radioGroup.checkedRadioButtonId == R.id.rbSquare) "square" else "circle"

            val intent = Intent(this, GameActivity::class.java)  // ① 인텐트 객체 생성
            intent.putExtra("shape", shape)
            launcher.launch(intent)                              // ② 두 번째 액티비티 실행
        })
    }
}