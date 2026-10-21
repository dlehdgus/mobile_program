package com.example.mp0604

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editUrl: EditText = findViewById(R.id.editUrl)
        val btnWeb: Button = findViewById(R.id.btnWeb)

        btnWeb.setOnClickListener(View.OnClickListener {
            var url = editUrl.text.toString().trim()
            if (url.isEmpty()) {
                Toast.makeText(this, "URL을 입력하세요", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            // "www.hanbit.co.kr" 처럼 입력해도 열리도록 스킴(http/https) 보정
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            val intent = Intent(Intent.ACTION_VIEW)   // 액션: 데이터 보여주기
            intent.data = Uri.parse(url)              // 데이터: 웹 주소(URI)
            try {
                startActivity(intent)                 // 처리할 수 있는 브라우저 앱 실행
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "실행할 수 있는 앱이 없습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }
}