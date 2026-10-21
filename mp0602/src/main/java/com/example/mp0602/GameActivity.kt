package com.example.mp0602

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// 두 번째 액티비티: 전달받은 그림으로 틀린 그림 찾기
class GameActivity : AppCompatActivity() {

    private var count = 0  // 찾은 개수

    // 틀린 곳의 위치 (가로 비율, 세로 비율) : 0.0 = 왼쪽/위, 1.0 = 오른쪽/아래
    // ※ 실제 이미지를 보고 값을 조금씩 조정하세요.
    private val catSpots = arrayOf(
        0.45f to 0.15f,   // 고양이 머리 위
        0.85f to 0.20f,   // 액자
        0.40f to 0.45f,   // 얼굴(눈)
        0.10f to 0.70f,   // 햄버거
        0.85f to 0.80f    // 오렌지
    )
    private val dogSpots = arrayOf(
        0.20f to 0.20f,
        0.70f to 0.15f,
        0.50f to 0.45f,
        0.25f to 0.75f,
        0.80f to 0.75f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ④ 인텐트 객체 받기  ⑤ 인텐트로 전달된 문자열 받기
        val intent = getIntent()
        val animal = intent.getStringExtra("animal") ?: "cat"

        val imgTop: ImageView = findViewById(R.id.imgTop)
        val imgBottom: ImageView = findViewById(R.id.imgBottom)
        val spots = arrayOf<ImageView>(
            findViewById(R.id.spot1), findViewById(R.id.spot2), findViewById(R.id.spot3),
            findViewById(R.id.spot4), findViewById(R.id.spot5)
        )

        // 선택한 그림에 따라 이미지와 정답 위치 설정
        val positions: Array<Pair<Float, Float>>
        if (animal == "dog") {
            imgTop.setImageResource(R.drawable.dog01)
            imgBottom.setImageResource(R.drawable.dog02)
            positions = dogSpots
        } else {
            imgTop.setImageResource(R.drawable.cat01)
            imgBottom.setImageResource(R.drawable.cat02)
            positions = catSpots
        }

        for (i in spots.indices) {
            // 정답 영역 위치 지정
            val params = spots[i].layoutParams as ConstraintLayout.LayoutParams
            params.horizontalBias = positions[i].first
            params.verticalBias = positions[i].second
            spots[i].layoutParams = params

            // 정답 영역 클릭 → 맞았습니다
            spots[i].setOnClickListener {
                if (spots[i].tag == "found") return@setOnClickListener  // 이미 찾은 곳
                spots[i].tag = "found"
                spots[i].setImageResource(R.drawable.ok_select)          // 빨간 동그라미 표시
                count++
                if (count == 5) {
                    Toast.makeText(this, "5개 모두 선택했습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 정답이 아닌 곳(그림의 나머지 부분) 클릭 → 틀렸습니다
        imgTop.setOnClickListener {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }

        // 재시작하기 → 첫 번째 액티비티로 돌아감
        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            finish()
        }
    }
}