package com.example.mp1004

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.insert)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var cateResult: String = ""
        val InsertAddBtn : Button = findViewById(R.id.button_insert)
        val InsertListBtn : Button = findViewById(R.id.button_list)
        val radio_group : RadioGroup = findViewById(R.id.radio_group)
        val purposeEditTxt : EditText = findViewById(R.id.edit_purpose)
        var DateString = ""
        val DateSetBtn : Button= findViewById(R.id.button_calendar)
        val TodayBtn : Button= findViewById(R.id.button_today)
        val moneyEditTxt :EditText= findViewById(R.id.edit_money)
        val contentEidtTxt :EditText= findViewById(R.id.edit_content)
        val dateTxt : TextView = findViewById(R.id.textview_date)
        printDate(dateTxt)                   //날짜 출력 초기화
        cateResult ="지출"                   //초기 카테고리는 지출로 설정
        //오늘 날짜 버튼 클릭 시 현재 날짜 출력
        TodayBtn.setOnClickListener {
            printDate(dateTxt)
        }
        //날짜 설정 버튼 클릭 시 DatePickerDialog 실행
        DateSetBtn.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                var monthStr:String=(i2+1).toString()
                var dayStr:String=i3.toString()
                if((i2 + 1)<10){
                    monthStr= "0"+(i2 + 1).toString()
                }
                if(i3<10){
                    dayStr = "0"+i3
                }
                dateTxt.text = i.toString() + "-" + monthStr + "-" + dayStr
            }
            val datePickerDialog =
                DatePickerDialog(this, listener, year, month, day)
            datePickerDialog.show()
        }
        //라디오 버튼 체크 변경 시 카테고리 값 설정
        radio_group.setOnCheckedChangeListener { group, chekcedId ->
            when (chekcedId) {
                R.id.radio_income -> cateResult = "수입"
                R.id.radio_expense -> cateResult = "지출"
            }
        }
        //추가 버튼 클릭 시 DB에 데이터 저장
        InsertAddBtn.setOnClickListener {
            DateString = dateTxt.text.toString()
            val purpose = purposeEditTxt.text.toString().trim()
            val money = moneyEditTxt.text.toString().toIntOrNull()
            //필수 항목 확인
            if (purpose.isEmpty()) {
                Toast.makeText(this, "용도를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (money == null) {
                Toast.makeText(this, "금액을 올바르게 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val houseKDT = HouseKeeping(
                0,
                cateResult,
                purpose,
                DateString,
                money,
                contentEidtTxt.text.toString().trim()
            )

            val dbHelper = DBHelper(this)
            dbHelper.insert(houseKDT)
            dbHelper.close()
            Toast.makeText(this.applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
            purposeEditTxt.text.clear()
            moneyEditTxt.text.clear()
            contentEidtTxt.text.clear()
        }
        //리스트 보기 버튼 클릭 시 기존 MainActivity로 돌아감
        InsertListBtn.setOnClickListener {
            finish()
        }
    }
    //현재 날짜를 yyyy-MM-dd 형식으로 텍스트 뷰에 출력하는 메서드
    fun printDate(dateTxt:TextView){
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)
        var monthStr:String=month.toString()
        var dayStr:String=day.toString()
        if( month <10){
            monthStr= "0"+month
            //달이 10보다 작을 경우 달 앞에 0을 붙임
        }
        if(day < 10){
            dayStr = "0"+day
        }   //일이 10보다 작을 경우 일 앞에 0을 붙임
        dateTxt.text = year.toString() +"-" + monthStr +"-"+dayStr
    }
}