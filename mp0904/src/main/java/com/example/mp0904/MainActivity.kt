package com.example.mp0904

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit  var fruitTotalText: TextView
    lateinit  var   listViewItems: ArrayList<Fruit>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //과일 데이터를 리스트에 추가
        listViewItems = ArrayList<Fruit>()
        listViewItems.add(Fruit(R.drawable.fruit1, 30))
        listViewItems.add(Fruit(R.drawable.fruit2, 40))
        listViewItems.add(Fruit(R.drawable.fruit3, 60))
        listViewItems.add(Fruit(R.drawable.fruit4, 80))
        listViewItems.add(Fruit(R.drawable.fruit5, 100))
        //가격 합계 초기화
        fruitTotalText = findViewById(R.id.total)
        fruitTotalText.text = "0"
        //RecyclerView 어댑터 설정
        val adapter = OrderAdapter(this, listViewItems)
        //RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.fruit_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        //선택 변경 리스너 설정
        adapter.setOnSelectChangedListener(object : OrderAdapter.OnSelectChangedListener {
            override fun onSelectChanged(item: ArrayList<Fruit>) {
                setFruitTotal()
            }
        })
    }
    //가격 합계 계산 메서드
    fun calculateFruitTotal() : Int{
        var fruitTotal = 0;
        for( order in   listViewItems ){
            fruitTotal += order.mPrice * order.mQuantity
        }
        return fruitTotal;
    }
    //계산된 가격 합계를 TextView에 반영하는 메서드
    fun setFruitTotal(){
        fruitTotalText.setText(calculateFruitTotal().toString())
    }
}