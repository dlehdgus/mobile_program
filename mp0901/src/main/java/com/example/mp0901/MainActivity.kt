package com.example.mp0901

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var listview: ListView
    lateinit var adapter : MyAdapter
    var catsList = ArrayList<MyItem>()
    var selectItemPos :Int =-1            //선택한 아이템의 위치 저장
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //데이터 소스 선언
        catsList.add(MyItem("아비시니안", "활발하고 온순한 성격", "3~5kg", R.drawable.abyssinian))
        catsList.add(MyItem("샴", "놀기 좋아하고 온순한 성격", "3.6~5.4kg", R.drawable.siamese))
        catsList.add(MyItem("앙고라", "예민하고 상냥한 성격", "3.7~7kg", R.drawable.angora))
        catsList.add(MyItem("랙돌", "느긋하고 온순한 성격", "5~9kg", R.drawable.regdol))
        catsList.add(MyItem("뱅갈", "충성심 강하고 상냥한 성격", "5~8kg", R.drawable.bengal))
        catsList.add(MyItem("스핑크스", "활발하고 사교성 좋은 성격", "4~5kg", R.drawable.sphinx))
        catsList.add(MyItem("페르시안", "조용하고 온순한 성격", "3.6~5.4kg", R.drawable.persian))
        val btInsert: Button = findViewById(R.id.button1)
        val btModify: Button = findViewById(R.id.button2)
        val btDelete: Button = findViewById(R.id.button3)
        listview = findViewById(R.id.listview)
        //어댑터 설정
        adapter = MyAdapter(catsList)
        listview.adapter = adapter
        //ListView 항목 클릭 시 선택된 항목 저장 및 Toast 메시지 출력
        listview.setOnItemClickListener { adapterView, view, i, l ->
            val itemValue : MyItem = adapterView?.getItemAtPosition(i)  as MyItem
            selectItemPos = i
            //선택한 항목을 체크 상태로 만들어 배경색을 유지
            listview.setItemChecked(i, true)
            Toast.makeText(
                applicationContext,
                itemValue.name + " " + itemValue.character,
                Toast.LENGTH_SHORT
            ).show()
        }
        //추가 버튼 클릭 이벤트
        btInsert.setOnClickListener {
            val count = adapter.getCount()
            catsList.add(
                MyItem(
                    "러시안블루" + (count + 1),
                    "온순하고 애교 많은 성격",
                    "3.5~5kg",
                    R.drawable.russianblue
                )
            )
            adapter.notifyDataSetChanged()
            //추가한 마지막 행이 보이도록 이동
            listview.smoothScrollToPosition(catsList.size - 1)
        }
        //수정 버튼 클릭 이벤트
        btModify.setOnClickListener {
            val checked = selectItemPos
            if (checked >= 0 && checked < catsList.size) {
                val alertDialog = AlertDialog.Builder(this)
                val inflater: LayoutInflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_layout, null)
                alertDialog.setView(dialogView)
                val dialog = alertDialog.show()
                val title: TextView = dialogView.findViewById(R.id.textView)
                title.text = "고양이 : " + catsList.get(checked).name
                val layout_delete: LinearLayout = dialogView.findViewById(R.id.layout_delete)
                val layout_modify: LinearLayout = dialogView.findViewById(R.id.layout_modify)
                layout_delete.isVisible =false
                layout_modify.isVisible = true
                val button_yes: Button = dialogView.findViewById(R.id.button_yes)
                val button_no: Button = dialogView.findViewById(R.id.button_no)
                button_yes.text = "수정"
                button_no.text = "취소"
                //수정 버튼 클릭 시
                button_yes.setOnClickListener {
                    val weight: EditText = dialogView.findViewById(R.id.name)
                    val weightText = weight.text.toString().trim()
                    //몸무게를 입력하지 않으면 수정하지 않음
                    if (weightText.isEmpty()) {
                        Toast.makeText(this, "몸무게를 입력하세요", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val item = catsList.get(checked)
                    catsList.set(checked, MyItem(item.name, item.character, "몸무게 " + weightText + "kg", item.image))
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                //취소 버튼 클릭 시
                button_no.setOnClickListener {
                    dialog.dismiss()
                }
            }
            else Toast.makeText(this, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
        }
        //삭제 버튼 클릭 이벤트
        btDelete.setOnClickListener {
            val checked = selectItemPos
            if (checked >= 0 && checked < catsList.size) {
                val alertDialog = AlertDialog.Builder(this)
                val inflater: LayoutInflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_layout, null)
                alertDialog.setView(dialogView)
                val dialog = alertDialog.show()
                val title: TextView = dialogView.findViewById(R.id.textView)
                title.text = "고양이 : " + catsList.get(checked).name
                val layout_delete: LinearLayout = dialogView.findViewById(R.id.layout_delete)
                val layout_modify: LinearLayout = dialogView.findViewById(R.id.layout_modify)
                layout_delete.isVisible =true
                layout_modify.isVisible = false
                val button_yes: Button = dialogView.findViewById(R.id.button_yes)
                val button_no: Button = dialogView.findViewById(R.id.button_no)
                button_yes.text = "예"
                button_no.text = "아니오"
                //삭제 버튼 클릭 시
                button_yes.setOnClickListener {
                    catsList.removeAt(checked)
                    //삭제 후에는 선택된 항목이 없도록 초기화
                    selectItemPos = -1
                    listview.clearChoices()
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                //취소 버튼 클릭 시
                button_no.setOnClickListener {
                    dialog.dismiss()
                }
            }
            else Toast.makeText(this, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
        }
    }
}
