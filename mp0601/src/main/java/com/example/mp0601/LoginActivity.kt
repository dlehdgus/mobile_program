package com.example.mp0601

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// 두 번째 액티비티: 로그인 화면
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)   // 대상 액티비티의 레이아웃 연결
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editId: EditText = findViewById(R.id.editId)
        val editPasswd: EditText = findViewById(R.id.editPasswd)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(View.OnClickListener {
            val id = editId.text.toString()
            val passwd = editPasswd.text.toString()
            Toast.makeText(this, "아이디 : $id\n비밀번호 : $passwd", Toast.LENGTH_SHORT).show()
        })
    }
}