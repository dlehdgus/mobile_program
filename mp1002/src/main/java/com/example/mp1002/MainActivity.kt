package com.example.mp1002

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var calendarView: CalendarView
    lateinit var editText: EditText
    lateinit var btnSave: Button
    lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        calendarView = findViewById(R.id.calendarView)
        editText = findViewById(R.id.edtContent)
        btnSave = findViewById(R.id.save)
        btnDelete = findViewById(R.id.delete)
        //오늘 날짜 구하기
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var month = Calendar.getInstance().get(Calendar.MONTH) + 1 //0부터 시작하므로 +1
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var fileName = "${year}_${month}_${day}.txt"      //파일 이름 형식은 yyyy_MM_dd.txt

        val diaryContent = readDiary(fileName)            //기존 파일 내용 불러오기
        editText.setText(diaryContent)
        btnSave.isEnabled = true
        //날짜 변경 이벤트
        calendarView.setOnDateChangeListener { _, newYear, newMonth, newDay ->
            year = newYear
            month = newMonth + 1
            day = newDay
            fileName = "${year}_${month}_${day}.txt"
            val content = readDiary(fileName)
            editText.setText(content)
            btnSave.isEnabled = true
        }
        //저장 버튼 클릭 이벤트
        btnSave.setOnClickListener {
            val outputStream: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            val content: String = editText.text.toString()
            outputStream.write(content.toByteArray())
            outputStream.close()
            btnSave.text = "수정하기"
            Toast.makeText(
                applicationContext,
                "$year/$month/$day 일정을 저장했습니다",
                Toast.LENGTH_SHORT
            ).show()
        }
        //삭제 버튼 클릭(선택한 날짜의 일정 파일 삭제)
        btnDelete.setOnClickListener {
            val message = if (deleteFile(fileName)) {
                "$year/$month/$day 일정을 삭제했습니다"
            } else {
                "삭제할 일정이 없습니다"
            }
            editText.text.clear()
            editText.hint = "일정 없음"
            btnSave.text = "저장하기"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    //다이어리 파일을 읽는 함수
    fun readDiary(filename: String): String {
        var content = ""
        try {
            val inputStream: FileInputStream = openFileInput(filename)
            val text = inputStream.reader().readText()
            inputStream.close()
            content = text.trim()
            btnSave.text = "수정하기" //파일이 존재하면 "수정하기"
        } catch (e: IOException) {
            //파일이 없을 경우
            editText.hint = "일정 없음"
            btnSave.text = "저장하기"
        }
        return content
    }
}