package com.example.mp0703

import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var signObj: ImageView
    lateinit var rightLayoutObj : LinearLayout
    //이미지 리소스 ID 배열
    var ivDrawableId = intArrayOf(
        R.drawable.image1,  R.drawable.image2,  R.drawable.image3,
        R.drawable.image4,  R.drawable.image5,  R.drawable.image6,
    )
    //ImageView ID 배열
    var imageviewId = intArrayOf(
        R.id.imageView1,  R.id.imageView2,  R.id.imageView3,
        R.id.imageView4,  R.id.imageView5,  R.id.imageView6
    )
    //ImageView 객체 배열
    var ivObj = arrayOfNulls<ImageView>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //초기 이미지 순서를 무작위로 섞기
        var sortImage = IntArray(6)
        for (i in 0..5) {
            sortImage[i] = i
        }
        for (i in 0..5) {
            val randNum = (Math.random() * 5).toInt()
            val swap = sortImage[randNum]
            sortImage[randNum] = sortImage[i]
            sortImage[i] = swap
        }
        //이미지 뷰에 이미지 설정
        for (i in 0..5) {
            ivObj[i] = findViewById(imageviewId[i])
            var index = sortImage[i];
            ivObj[i]?.setImageResource(ivDrawableId[index])
        }
        signObj = findViewById(R.id.imageView)
        rightLayoutObj = findViewById(R.id.rightLayout)
        signObj.visibility = View.INVISIBLE
        val button: TextView = findViewById(R.id.button)
        button.setOnClickListener {
            //팝업 메뉴 표시
            val popupMenu: PopupMenu = PopupMenu(applicationContext, button)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.show()
            //메뉴 항목 클릭 리스너 설정
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    //오름차순 정렬
                    R.id.menu01 -> {
                        for (i in 0..5)
                            ivObj[i]?.setImageResource(ivDrawableId[i])
                        signObj.visibility = View.VISIBLE
                        signObj.setImageResource(R.drawable.bg_small2big)
                        rightLayoutObj.visibility = View.GONE
                        Toast.makeText(this, "오름차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                    //내림차순 정렬
                    R.id.menu02 -> {
                        for (i in 0..5)
                            ivObj[i]?.setImageResource(ivDrawableId[5 - i])
                        signObj.visibility = View.VISIBLE
                        signObj.setImageResource(R.drawable.bg_big2small)
                        rightLayoutObj.visibility = View.GONE
                        Toast.makeText(this, "내림차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }
}