package com.example.mp1004

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var findRecyclerView: RecyclerView
    private var fromDate: String? = null    //검색 시작일
    private var toDate: String? = null      //검색 종료일

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val findFromTxt : TextView = findViewById(R.id.findFromTxt)
        val findToTxt : TextView= findViewById(R.id.findToTxt)
        val insertBtn : Button = findViewById(R.id.button_insert)
        val searchBtn : Button= findViewById(R.id.button_search)
        dbHelper = DBHelper(this)
        findRecyclerView = findViewById(R.id.FindRecyclerView)
        //선형 레이아웃 매니저를 사용하여 RecyclerView 리스트 형태로 출력
        findRecyclerView.layoutManager = LinearLayoutManager(this)
        findRecyclerView.setHasFixedSize(true)

        //검색 버튼 클릭 시 날짜 선택 후 해당 기간의 데이터 조회
        searchBtn.setOnClickListener {
            //MaterialDatePicker를 사용해 날짜 범위 선택 대화상자 생성
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val picker = builder.build()
            //날짜 범위 선택 후 처리
            picker.addOnPositiveButtonClickListener {
                //선택된 기간의 시작 및 종료 날짜를 yyyy-MM-dd 형식으로 변환
                //MaterialDatePicker의 값은 UTC 기준이므로 UTC로 변환해야 날짜가 밀리지 않음
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                format.timeZone = TimeZone.getTimeZone("UTC")
                fromDate = format.format(it.first)
                toDate = format.format(it.second)
                findFromTxt.text = fromDate
                findToTxt.text = toDate
                showList()
            }
            picker.show(supportFragmentManager, picker.toString())  //날짜 선택 대화상자 표시
        }
        //입력 버튼 클릭 시 InsertActivity로 이동
        insertBtn.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
    }

    //가계부 작성 후 돌아오면 검색 결과를 다시 조회
    override fun onResume() {
        super.onResume()
        showList()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    //DBHelper를 통해 검색 기간의 데이터를 조회하여 RecyclerView에 표시
    private fun showList() {
        val from = fromDate ?: return
        val to = toDate ?: return
        findRecyclerView.adapter = RecyclerViewAdapter(this, dbHelper.findTime(from, to))
    }
}