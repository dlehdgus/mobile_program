package com.example.mp0603

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// 두 번째 액티비티: 그림을 최대 3개 선택하고 결과를 첫 번째 액티비티로 돌려줌
class GameActivity : AppCompatActivity() {

    private val selected = ArrayList<Int>()   // 선택한 그림의 drawable id 목록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = getIntent()                              // ③ 인텐트 객체 받기
        val shape = intent.getStringExtra("shape") ?: "circle"

        // 선택한 도형 표시
        val imgShape: ImageView = findViewById(R.id.imgShape)
        imgShape.setImageResource(if (shape == "square") R.drawable.square else R.drawable.circle)

        val frameIds = intArrayOf(R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4, R.id.frame5,
            R.id.frame6, R.id.frame7, R.id.frame8, R.id.frame9)
        val checkIds = intArrayOf(R.id.check1, R.id.check2, R.id.check3, R.id.check4, R.id.check5,
            R.id.check6, R.id.check7, R.id.check8, R.id.check9)
        val shapeImages = intArrayOf(R.drawable.shape01, R.drawable.shape02, R.drawable.shape03,
            R.drawable.shape04, R.drawable.shape05, R.drawable.shape06, R.drawable.shape07,
            R.drawable.shape08, R.drawable.shape09)

        for (i in frameIds.indices) {
            val frame: FrameLayout = findViewById(frameIds[i])
            val check: ImageView = findViewById(checkIds[i])

            frame.setOnClickListener {
                if (check.visibility == View.VISIBLE) {
                    // 이미 선택된 그림 → 선택 취소
                    check.visibility = View.INVISIBLE
                    selected.remove(shapeImages[i])
                } else if (selected.size < 3) {
                    // 새로 선택 (3개까지)
                    check.visibility = View.VISIBLE
                    selected.add(shapeImages[i])
                } else {
                    Toast.makeText(this, "3개까지만 선택할 수 있습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnResult).setOnClickListener {
            intent.putExtra("selected", selected.toIntArray())  // ④ 전달할 데이터 추가
            intent.putExtra("shape", shape)
            setResult(RESULT_OK, intent)                         // ⑤ 인텐트 객체 전달
            finish()                                             // ⑥ 액티비티 종료
        }
    }
}