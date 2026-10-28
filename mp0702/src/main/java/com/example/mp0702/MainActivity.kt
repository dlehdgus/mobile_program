package com.example.mp0702

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit  var ivObj01 : ImageView
    lateinit  var ivObj02 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivObj01  = findViewById(R.id.ivSign1)
        ivObj02 = findViewById(R.id.ivSign2)
        // 각 이미지 뷰에 대해 컨텍스트 메뉴 등록
        registerForContextMenu(ivObj01)
        registerForContextMenu(ivObj02)
    }

    // 컨텍스트 메뉴 생성
    override fun onCreateContextMenu(menu: ContextMenu?,  v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo? ) {
        when (v?.id) {
            R.id.ivSign1 -> {
                menu?.setHeaderTitle("고양이 비교 메뉴")
                menuInflater.inflate(R.menu.cat_menu, menu) // 고양이 메뉴 항목 설정
            }
            R.id.ivSign2 -> {
                menu?.setHeaderTitle("강아지 비교 메뉴")
                menuInflater.inflate(R.menu.dog_menu, menu) // 강아지 메뉴 항목 설정
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    // 컨텍스트 메뉴 항목 선택 시 동작
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // 고양이 메뉴 선택
            R.id.item11 -> {
                ivObj01.setImageResource(R.drawable.sign_great)
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item12 -> {
                ivObj01.setImageResource(R.drawable.sign_small)
                Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item13 -> {
                ivObj01.setImageResource(R.drawable.sign_equal)
                Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                return true
            }
            // 강아지 메뉴 선택
            R.id.item21 -> {
                ivObj02.setImageResource(R.drawable.sign_great)
                Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item22 -> {
                ivObj02.setImageResource(R.drawable.sign_small)
                Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item23 -> {
                ivObj02.setImageResource(R.drawable.sign_equal)
                Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}