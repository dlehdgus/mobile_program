package com.example.mp0605

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

        val editTo: EditText = findViewById(R.id.editTo)
        val editSubject: EditText = findViewById(R.id.editSubject)
        val editContent: EditText = findViewById(R.id.editContent)
        val btnSend: Button = findViewById(R.id.btnSend)

        btnSend.setOnClickListener(View.OnClickListener {
            // ACTION_SEND : 데이터를 다른 앱으로 보내기
            // → 인텐트 필터에 ACTION_SEND + text/plain 을 등록한 앱(Gmail, Outlook, Drive 등)이 후보가 됨
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"                                             // <data> MIME 타입
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(editTo.text.toString()))   // 받는 사람(배열)
            intent.putExtra(Intent.EXTRA_SUBJECT, editSubject.text.toString())     // 제목
            intent.putExtra(Intent.EXTRA_TEXT, editContent.text.toString())        // 내용

            // 앱 선택 화면(공유 시트)을 띄워서 사용자가 이메일 앱을 고르게 함
            startActivity(Intent.createChooser(intent, "이메일 앱 선택"))
        })
    }
}